package com.Marry;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.client.RemoteMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Marry {
	Marry(){
		;
	}
	static void SayMarry(String str){
		System.out.print("start playing...\n");
		MaryInterface marytts=null;
		AudioInputStream audio=null;
		try {
			marytts = new LocalMaryInterface();
		} catch (MaryConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		marytts.setVoice("cmu-bdl-hsmm");
		
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
		Matcher reMatcher = re.matcher(str);
		try {
			audio = marytts.generateAudio("let me think.");
		} catch (SynthesisException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (reMatcher.find()) {
			String str1=reMatcher.group();
		    System.out.println(reMatcher.group());
		    AudioPlayer player = new AudioPlayer(audio);
	        player.start(); 
		    try {
				 audio = marytts.generateAudio(str1);
			} catch (SynthesisException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				player.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("audioplayer finished...\n");
		}     
		
        //while (!player.)
		System.out.print("abc");
		//MaryAudioUtils.writeWavFile(MaryAudioUtils.getSamplesAsDoubleArray(audio), "/tmp/thisIsMyText.wav", audio.getFormat());
	}

	
	public static void main(String[] args) throws IOException {
		String ask="what color is the orange.";
		GoogleSearch GS=new GoogleSearch(ask);
		System.out.print("question:"+ask+"\n");
		String str=GS.getAnswer();
		//System.out.print("  answer:"+st);	
		SayMarry(str);
		
	}
}




 class GoogleSearch {

	
	//public static final String GOOGLE_SEARCH_URL = "https://www.google.com/";
	
	
	String google = "http://www.google.com/search?q=";
	String charset = "UTF-8";
	String search = "what is the color of orange";
	String getAnswer() throws UnsupportedEncodingException, IOException{
		String resultText="";
		Document doc = Jsoup.connect(google+URLEncoder.encode(search, charset)).userAgent("Mozilla/5.0").get();

		//System.out.println(doc.html());
		//Elements results = doc.select("h3.r > a");
		//for (Element result : results) {
		//	String linkHref = result.attr("href");
		//	String linkText = result.text();
		//	System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
		//}
		//System.out.print("\n-------\n\n");
		
		//Elements results1 = doc.select("span.st");
		//for (Element result : results1) {

		//	String linkText = result.text();
		//	System.out.println("Text::" + linkText );

		//}
		//System.out.print("\n-------\n\n");
		
		//Elements results2 = doc.select("div._o0d");
		//for (Element result : results2) {
		//	resultText=resultText+result.text()+" ";
		//	//System.out.println("Text::" + linkText );
		//}
		
		Elements results3 = doc.select("div.g");
		for (Element result : results3) {
			resultText=result.text();
			break;
			//System.out.println("Text::" + linkText );
		}
		//return resultText; 
		return resultText;
		
	}
	GoogleSearch(String google) throws IOException
	{
		this.search=google;
		//String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!	
	}
}
