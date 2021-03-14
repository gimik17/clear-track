import java.awt.Color;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class HtmlDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditorPane bottom;
	
	public HtmlDialog(String title)
	{
		this.setTitle(title);
		Container content = this.getContentPane();
        content.setBackground(Color.white);
        
        bottom = new JEditorPane();
        bottom.setEditable(false);
        bottom.setContentType("text/html");
        
        JScrollPane bottomScrollPane = new JScrollPane(bottom);
        bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        content.add(bottomScrollPane);
	}

    public void setContent(String htmlContent) {
        this.bottom.setText(htmlContent);
    }
}
