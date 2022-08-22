package org.ovgu.de.fiction.utils;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

/**
 * @author Suhita
 *
 */
public class StanfordPipeline {

	private static StanfordCoreNLP pipeline;

	/**
	 * @return
	 */
	public static StanfordCoreNLP getPipeline(String annotations) {
		if (pipeline != null)
			return pipeline;

		Properties properties = new Properties();
		if (annotations == null) {
			properties.put(FRConstants.STNFRD_ANNOTATOR, FRConstants.STNFRD_LEMMA_ANNOTATIONS);
		}
//		else if(annotations.equals(FRConstants.STNFRD_QUOTE_ANNOTATIONS)) {
//			properties.put(FRConstants.STNFRD_ANNOTATOR, FRConstants.STNFRD_QUOTE_ANNOTATIONS);
//    		properties.put("ner.applyFineGrained", "false");
//    		//properties.put("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");			
//		}
		else if(annotations.equals(FRConstants.STNFRD_COREF_ANNOTATIONS)) {
			properties.put(FRConstants.STNFRD_ANNOTATOR, FRConstants.STNFRD_COREF_ANNOTATIONS);
			properties.put("ner.applyFineGrained", "false");			
		}
		else
			properties.put(FRConstants.STNFRD_ANNOTATOR, annotations);
		properties.put("ner.useSUTime", "false ");
		properties.put("ner.applyNumericClassifiers", "false");
		properties.put("ner.applyFineGrained", "false");

		RedwoodConfiguration.current().clear().apply();

		if(annotations!=null && annotations.contains("parse")){
			properties.put("depparse.model", "edu/stanford/nlp/models/parser/nndep/english_SD.gz");
			//properties.put("parse.maxlen", "30");
		}
		return new StanfordCoreNLP(properties);
	}

	public static void resetPipeline() {
		pipeline = null;
	}
	
	public static StanfordCoreNLP getQuotePipeline() {		
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,coref,entitymentions,quote");
		properties.put("ner.useSUTime", "false ");
		properties.put("ner.applyNumericClassifiers", "false");
		properties.put("ner.applyFineGrained", "false");
		properties.put("parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz");
		StanfordCoreNLP quotePipeline = new StanfordCoreNLP(properties);
		return quotePipeline;		
	}
	
	public static StanfordCoreNLP getCorefPipeline() {
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		properties.put("ner.applyFineGrained", "false");	
		StanfordCoreNLP corefPipeline = new StanfordCoreNLP(properties);
		return corefPipeline;
	}
	
	public static StanfordCoreNLP getGermanPipeline() {
		StanfordCoreNLP pipeline = new StanfordCoreNLP("german");
		return pipeline;
	}
}
