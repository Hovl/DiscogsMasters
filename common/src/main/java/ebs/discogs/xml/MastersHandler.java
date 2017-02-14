package ebs.discogs.xml;

import ebs.discogs.data.ReleasesWorker;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 16:22
 * Copyright (c) 2014
 */
public class MastersHandler extends DefaultHandler {
	private static Logger logger = Logger.getLogger(MastersHandler.class.getName());

	private Integer currentYear;
	private String currentGenre;

	private boolean readYear = false;
	private boolean readGenre = false;

	private ReleasesWorker releasesWorker;

	MastersHandler(ReleasesWorker releasesWorker) {
		this.releasesWorker = releasesWorker;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("master".equals(qName)) {
			currentYear = null;
			currentGenre = null;
		} else if ("genre".equals(qName)) {
			readGenre = true;
		} else if ("year".equals(qName)) {
			readYear = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(readGenre) {
			if(currentGenre == null) {
				currentGenre = new String(ch, start, length);
			}
			readGenre = false;
		} else if(readYear) {
			if(currentYear == null) {
				currentYear = Integer.parseInt(new String(ch, start, length));
			}
			readYear = false;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("master".equals(qName)) {
			releasesWorker.incrementRelease(currentGenre, currentYear);
		}
	}
}
