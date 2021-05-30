package datastructure.core.text;

/**
 *
 * @author aabnar
 * @param <T>
 * @param <L>
 * @param <C>
 * @param <A>
 */
public class AnalyzedMsg<T extends MsgNode<A, L>, L, C,A> {
	
	enum Type {
		
	}
	
	T prossedMsg;
	
    /**
     *
     * @param pProcessedMsg
     */
    public AnalyzedMsg(T pProcessedMsg) {
		this.prossedMsg = pProcessedMsg;
	}
	
    /**
     *
     * @return
     */
    public T getProcessedMsg() {
		return prossedMsg;
		
	}

}
