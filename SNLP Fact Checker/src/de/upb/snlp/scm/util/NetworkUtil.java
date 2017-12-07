package de.upb.snlp.scm.util;

import org.sweble.wikitext.engine.EngineException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.parser.LinkTargetException;

public class NetworkUtil {

	public static void main(String[] args) {
		String wikiText = FileUtil.readFile("C:\\Users\\Kadiray\\SNLP\\data\\wikitext.txt");
		
		try {
			System.out.println(convertWikiText("Albert Einstein", wikiText, 9999));
		} catch (LinkTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String convertWikiText(String title, String wikiText, int maxLineLength)
			throws LinkTargetException, EngineException {
		// Set-up a simple wiki configuration
		WikiConfig config = DefaultConfigEnWp.generate();
		// Instantiate a compiler for wiki pages
		WtEngineImpl engine = new WtEngineImpl(config);
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, title);
		PageId pageId = new PageId(pageTitle, -1);
		// Compile the retrieved page
		EngProcessedPage cp = engine.postprocess(pageId, wikiText, null);
		TextConverter p = new TextConverter(config, maxLineLength);
		return (String) p.go(cp.getPage());
	}

}
