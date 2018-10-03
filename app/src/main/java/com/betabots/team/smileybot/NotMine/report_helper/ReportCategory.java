package com.betabots.team.smileybot.NotMine.report_helper;

import java.util.ArrayList;

/*
		Created by:Rana Depto

		Email:mail@ranadepto.com

	    Date:6/2/18,Time:4:24PM
*/

public class ReportCategory
{
    int i;

    public String getReportCategory(String scannedWords)
    {
        if(isCBC(scannedWords))
            return "CBC";
        if(isURINE(scannedWords))
            return "URINE";
        if(isSTOOL(scannedWords))
            return "STOOL";

        return "UNKNOWN";
    }

    private boolean isCBC(String scannedWords)
    {
        ArrayList<String> attributeKeywordList=new ArrayList<>();
        attributeKeywordList.add("haemoglobin");
        attributeKeywordList.add("hgb");
        attributeKeywordList.add("esr");
        attributeKeywordList.add("hct");
        attributeKeywordList.add("mcv");
        attributeKeywordList.add("mch");
        attributeKeywordList.add("mchc");
        attributeKeywordList.add("pdw");
        attributeKeywordList.add("mpv");
        attributeKeywordList.add("wbc");
        attributeKeywordList.add("p-lcr");
        attributeKeywordList.add("rdw-cv");
        attributeKeywordList.add("platelet");
        attributeKeywordList.add("neutrophil");
        attributeKeywordList.add("lymphocyte");
        attributeKeywordList.add("eosinophil");
        attributeKeywordList.add("basophil");
        attributeKeywordList.add("monocyte");
        attributeKeywordList.add("blast");
        attributeKeywordList.add("malarial");
        attributeKeywordList.add("parasite");
        attributeKeywordList.add("h.g.b");
        attributeKeywordList.add("e.s.r");
        attributeKeywordList.add("h.c.t");
        attributeKeywordList.add("m.c.v");
        attributeKeywordList.add("m.c.h");
        attributeKeywordList.add("m.c.h.c");
        attributeKeywordList.add("p.d.w");
        attributeKeywordList.add("m.p.v");
        attributeKeywordList.add("w.b.c");

        return checkattributeKeywordList(scannedWords, attributeKeywordList, 5);
    }

    private boolean isURINE(String scannedWords)
    {
        ArrayList<String> attributeKeywordList=new ArrayList<>();
        attributeKeywordList.add("appearance");
        attributeKeywordList.add("sediment");
        attributeKeywordList.add("gravity");
        attributeKeywordList.add("phosphate");
        attributeKeywordList.add("albumin");
        attributeKeywordList.add("sugar");
        attributeKeywordList.add("candida");
        attributeKeywordList.add("oxalate");
        attributeKeywordList.add("crystal");
        attributeKeywordList.add("amorphous");
        attributeKeywordList.add("spermatozoa");
        attributeKeywordList.add("granular");
        attributeKeywordList.add("trichomonas");
        attributeKeywordList.add("virginal");
        attributeKeywordList.add("bacteria");

        return checkattributeKeywordList(scannedWords, attributeKeywordList, 5);
    }

    private boolean isSTOOL(String scannedWords)
    {
        ArrayList<String> attributeKeywordList=new ArrayList<>();
        attributeKeywordList.add("stool");
        attributeKeywordList.add("consisten");
        attributeKeywordList.add("mucas");
        attributeKeywordList.add("occult");
        attributeKeywordList.add("substance");
        attributeKeywordList.add("vegetable");
        attributeKeywordList.add("starch");
        attributeKeywordList.add("ova");
        attributeKeywordList.add("cyst");
        attributeKeywordList.add("trophozoite");

        return checkattributeKeywordList(scannedWords, attributeKeywordList, 4);
    }

    private boolean checkattributeKeywordList(String scannedWords, ArrayList<String> attributeKeywordList, int thresholdValue)
    {
        int count=0;

        //Counting how many attributeKeywordList are being found.
        for(i=0;i<attributeKeywordList.size();i++)
        {
            if(scannedWords.toLowerCase().contains(attributeKeywordList.get(i)))
            {
                count++;
                //Minimum number of attributeKeywordList should be found, if found, then return true.
                if(count>=thresholdValue)
                    return true;
            }
        }

        return false;
    }

}
