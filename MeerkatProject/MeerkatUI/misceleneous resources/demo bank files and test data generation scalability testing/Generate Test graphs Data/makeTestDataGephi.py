"""
graph
[
  node
  [
   id A
  ]
  node
  [
   id B
  ]
  node
  [
   id C
  ]
   edge
  [
   source B
   target A
  ]
  edge
  [
   source C
   target A
  ]
]
"""
import random;



def generate_vertices(no_of_vertices):
    for i in range(1, no_of_vertices+1):
        lines.append("  " + "node" + '\n')
        lines.append("  " + "[" + '\n')
        vertexLine = get_vertex_line(i)
        lines.append(vertexLine + '\n')
        lines.append("  " + "]" + '\n')
        
        
def get_vertex_line(vertex_no):
    vertex_line = "   " + "id" + " " + str(vertex_no);
    return vertex_line;
    
def get_edge_line_source(vertex_no_from):
    edge_line_source = "   " + "source" + " " + str(vertex_no_from);
    return edge_line_source;
 
def get_edge_line_target(vertex_no_to):
    edge_line_target = "   " + "target" + " " + str(vertex_no_to);
    return edge_line_target;
    
    
def generate_edges(no_of_vertices, no_of_edges):
    for i in range(no_of_edges):
        vertex_no_from, vertex_no_to = random.sample(range(1, no_of_vertices), 2)
        #edge_weight = get_edge_weight()
        lines.append("  " + "edge" + '\n')
        lines.append("  " + "[" + '\n')
        edge_line_source  = get_edge_line_source(vertex_no_from)
        edge_line_target  = get_edge_line_target(vertex_no_to)
        lines.append(edge_line_source + '\n')
        lines.append(edge_line_target + '\n')
        lines.append("  " + "]" + '\n')
        
def get_edge_weight():
        weight = random.randint(1,100000)*random.random()
        return weight
    
def write_to_file(filename, linesArray):
    with open(filename, 'w') as f:
        for s in linesArray:
            f.write(s)
            
            
            
no_of_vertices = 100000;
no_of_edges = 130000;
filename = "gephi-testdata-100k.gml"


lines = [];
lines.append("graph"  + '\n');
lines.append("["  + '\n');

generate_vertices(no_of_vertices);


generate_edges(no_of_vertices, no_of_edges);

lines.append("]"  + '\n');

write_to_file(filename,lines)

