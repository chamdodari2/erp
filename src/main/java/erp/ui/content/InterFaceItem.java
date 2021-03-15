package erp.ui.content;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class InterFaceItem<T>extends JPanel { //1.
	
	
	public abstract void initialize();
	
	public abstract void setItem(T item);
	public abstract T getItem();
	public abstract void validCheck();
	public abstract void clearTf();
	
}