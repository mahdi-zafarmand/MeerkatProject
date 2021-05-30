package algorithm.graph.metric;

import datastructure.core.TimeFrame;
import datastructure.core.graph.classinterface.IDynamicGraph;
import datastructure.core.graph.classinterface.IEdge;
import datastructure.core.graph.classinterface.IStaticGraph;
import datastructure.core.graph.classinterface.IVertex;
import java.util.HashMap;

/**
 *
 * @author aabnar
 * @param <V>
 * @param <E>
 */
public class NormalizedAlphaCentrality<V extends IVertex, E extends IEdge<V>> extends IMetric<V,E> {
	
    /**
     *
     */
    public static final String STR_NAME = "Normalized Alpha Centrality";


    private NormalizedAlphaCentrality(IDynamicGraph<V, E> pIGraph, TimeFrame tf, String[] parameters) {
        super(pIGraph, tf, parameters);
    }

    /**
     *
     */
    @Override
    public void run() {
        blnDone = false;
        compute(dynaGraph, tf, 0.15);	
    }

    /**
     *
     * @param graph
     * @param tf
     * @param alpha
     */
    public void compute(final IDynamicGraph<V,E> graph, TimeFrame tf, Double alpha) {

    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Normalized Alpha";
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<V, Double> getScores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void writeToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param parameters
     */
    @Override
    protected void parseParameters(String[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isResultValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This is a path-based ranking measure, with non-conservative flow; that
     * is, it computes a centrality measure for each node based off of a
     * simulation of connections that allow propagation if free-copying
     * attributes (e.g. information, as opposed to wealth).
     * 
     * The Alpha parameter is used as a relative weighting for endogenous versus
     * exogenous factors. Exogenous factors are expressed through the
     * personalization vector v. Alpha values approaching 1.0 primarily focus on
     * endogenous factors, meaning that the centrality measures computed will be
     * based on the network structure and not on the personalization vector.
     * Examples of personalization vector use include converting the number of
     * awards an actor has received into weights, when looking at a network of
     * actors, where edges are co-appearance in film (my example).
     * 
     * @see "R. Ghosh, K. Lerman. Predicting Influential Users in Online Social Networks. 4th SNA-KDD Workshop '10"
     * @see "P. Bonacich. Eigenvector-like measures of centrality for assymetric relations. Social Networks, 23:191-201, 2001."
     * @see "L. Katz. A new status derived from sociometric analysis. Psychometrika, 18:39-43, 1953."
     */
    private class NormalizedAlpha<V extends IVertex, E extends IEdge<V>> {
        /**
         * Weighting for endogenous (network structural) versus exogenous
         * (entity attributes as weights in exogenousV, personalization vector).
         */
        private double alpha = 1.0;



        /**
         * This stores the sum of the centrality values produced in the inner
         * loop (update() method), for use in afterStep() when we want to
         * normalize the scores.
         */
        private double centralitySum = 0.0;


        /**
         * Creates an instance with the specified graph, edge weights, vertex
         * priors, and 'random jump' probability (alpha).
         * 
         * @param graph
         *            the input graph
         * @param edge_weights
         *            the edge weights, denoting transition probabilities from
         *            source to destination
         * @param v
         *            the personalization vector v. Can be null or empty.
         *            Represents exogenous factors to be considered while
         *            computing the centrality values.
         * @param stepSize
         *            Initial alpha, the weighting of endogenous versus
         *            exogenous factors. Values approaching 1.0 weights towards
         *            network structure over the weight vector v
         *            (personalization vector).
         */
        public NormalizedAlpha(IStaticGraph<V, E> pIGraph, Double pdblAlpha) {

            if (pdblAlpha != null) {
                this.alpha = pdblAlpha;
            }
            initialize();
        }

        private void initialize() {
                // TODO Auto-generated method stub

        }

        protected double update(V v) {
            // PageRankWithPriors update() method has some code relevant to
            // hypergraphs, which I do not expect Meerkat to deal with yet.

            /*
             * Note that Ghosh's pseudocode is abundant with super and
             * subscripts. The alpha-n subscript to C, and the t subscript to
             * alpha are not helpful,for our purpose because the pseudocode is
             * actually for their experimental preparation, not simpy for
             * Normalized-alpha.
             */

            /*
             * Compute this vertex's next value The full on equation for
             * matrices is like: CentralityVector <- exogenousV +
             * CentralityVector*AjacencyMatrix but we're not working at a matrix
             * scope with this implementation. Instead we'll be multiplying
             * alpha against the column of A corresponding to the vertex, and
             * summing those. This column is the incoming connections from the
             * current vertex. Expressed as column sums matches Katz (1953).
             */

            // newValue is the equivalent to a single atom of the C row matrix
            // that appears in Ghosh's
            // pseudocode. Update is called once per vertex.
            // TODO Could this have the side effect of using updated data
            // 'early'? Would that really matter? It might!
            
            double newValue = 0;
            double currentScore = 0;
            /*            
            for (E edge : graph.getInEdges(v)) {
                if (edge_transformer != null) {
                        newValue += edge_transformer.transform(edge);
                } else {
                        newValue += ((IEdge) edge).getWeigher().getWeight();
                }
            }
            // v + alpha*C*A is in pseudocode turns to v + alpha * C for this
            // vertex * sum of adjacency values for this vertex.
            // I factored the multiplication out and just did the edge weight
            // summation in the loop above.
            Double currentScore = getOutputValue(v);
            if (currentScore == null) {
                currentScore = this.exogenousV.transform(v);
            }
            newValue = this.exogenousV.transform(v) + this.alpha * currentScore * newValue;

            // We're done for a step's inner loop. Outside of this, in
            // afterStep(), we will
            // normalize this Katz/Bonacich result according to the Ghosh et al
            // formulation.
            // This cannot be done here since this method is stepping over
            // individual vertices
            // with the step halt criterion outside of the call. But we can
            // accumulate the centralities here!
            centralitySum += newValue;

            // Remember the new value for real.
            setOutputValue(v, newValue);

            // Give info for use in stopping criterion.
            */
            return Math.abs(currentScore - newValue);
        }

        /**
         * Cleans up after each step. In Ghosh's algorithm, nothing happens
         * here.
         */
        protected void afterStep() {
            // Do not confuse the algorithm used by Ghosh. The normalization
            // happens at
            // the very ending of the computation, not within any loop.
            // Their outer loop is actually outside of the outer loop of the
            // AbstractIterativeScorer.
            // That is, their outer loop is for their experiment looking at the
            // values of alpha.
            // I thought i would document that here to prevent confusion later,
            // because I was confused
            // and thought that the normalization would happen here in
            // afterStep().
            if (!updateDataStructure()) {
                    centralitySum = 0.0;
            }
        }

        /**
         * Steps through this scoring algorithm until a termination condition is
         * reached.
         * 
         * This version also normalizes the scores as a final step in the
         * computation.
         */
        public void evaluate() {
            // Normalize the final scores
            /*
            double normalizedScore;
            for (V vertex : graph.getVertices()) {
                normalizedScore = this.centralitySum == 0 ? 0.0 : this
                                .getOutputValue(vertex) / this.centralitySum;
                this.setOutputValue(vertex, normalizedScore);
            }
                    */
        }

    }

}
