package com.betabots.team.smileybot.NotMine.report_helper;

import java.util.ArrayList;

/*
		Created by:Rana Depto

		Email:mail@ranadepto.com

	    Date:6/2/18,Time:11:24PM
*/

public class RearrangeScannedReportData
{
	int i, j, k;

	public ArrayList<AttributeValue> getUrineResult(ArrayList<String> scannedLineList)
	{
		ArrayList<AttributeValue> resultList = new ArrayList<>();
		resultList.add(new AttributeValue("Color", "", 0));
		resultList.add(new AttributeValue("Appearance", "", 0));
		resultList.add(new AttributeValue("Sediment", "", 0));
		resultList.add(new AttributeValue("Specific Gravity", "", 0));
		resultList.add(new AttributeValue("Reaction", "", 0));
		resultList.add(new AttributeValue("Excess of Phosphate", "", 0));
		resultList.add(new AttributeValue("Albumin", "", 0));
		resultList.add(new AttributeValue("Sugar (R/S)", "", 0));
		resultList.add(new AttributeValue("Pus Cells", "", 0));
		resultList.add(new AttributeValue("R.B.C.", "", 0));
		resultList.add(new AttributeValue("Epithelial Cells", "", 0));
		resultList.add(new AttributeValue("Candida", "", 0));
		resultList.add(new AttributeValue("Calcium Oxalate Crystals", "", 0));
		resultList.add(new AttributeValue("Triple Phosphate", "", 0));
		resultList.add(new AttributeValue("Amorphous Phosphate", "", 0));
		resultList.add(new AttributeValue("Spermatozoa", "", 0));
		resultList.add(new AttributeValue("Granular Cast", "", 0));
		resultList.add(new AttributeValue("Trichomonas Virginals", "", 0));
		resultList.add(new AttributeValue("Bactria", "", 0));


		//Attribute keyword list
		ArrayList<AttributeValueList> attributeKeywordArraylist = new ArrayList<>();
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.get(0).attributeKeywordList.add("color");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("appearance");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("pearan");
		attributeKeywordArraylist.get(2).attributeKeywordList.add("sediment");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("specific");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("gravity");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("reaction");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("phosphate");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("excess of");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("of phosp");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("albumin");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("bumin");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("sugar");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("r/s");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("pus cell");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("r.b");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("rbc");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("epithelial");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("thelial");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("epithe");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("candida");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("andid");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("oxalate");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("late crys");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("calcium ox");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("triple");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("ple phos");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("amorphous");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("rphous phos");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("sperma");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("matozo");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("granular");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("ular cast");
		attributeKeywordArraylist.get(17).attributeKeywordList.add("virginal");
		attributeKeywordArraylist.get(17).attributeKeywordList.add("virginal");
		attributeKeywordArraylist.get(18).attributeKeywordList.add("trichom");
		attributeKeywordArraylist.get(18).attributeKeywordList.add("monas virg");


		//Value keyword list
		ArrayList<String> valueKeywordList = new ArrayList<>();
		valueKeywordList.add("nil");
		valueKeywordList.add("straw");
		valueKeywordList.add("clear");
		valueKeywordList.add("acidic");
		valueKeywordList.add("qns");
		valueKeywordList.add("q.n.s");
		valueKeywordList.add("hpf");
		valueKeywordList.add("/hp");

		//Now go home boy!
		return rearrangeResult(scannedLineList, attributeKeywordArraylist, valueKeywordList, resultList, "URINE");
	}

	public ArrayList<AttributeValue> getStoolResult(ArrayList<String> scannedLineList)
	{
		ArrayList<AttributeValue> resultList = new ArrayList<>();
		resultList.add(new AttributeValue("Consistency", "", 0));
		resultList.add(new AttributeValue("Color", "", 0));
		resultList.add(new AttributeValue("Mucus", "", 0));
		resultList.add(new AttributeValue("Blood", "", 0));
		resultList.add(new AttributeValue("Reaction", "", 0));
		resultList.add(new AttributeValue("Occult Blood Test", "", 0));
		resultList.add(new AttributeValue("Reducing Substance", "", 0));
		resultList.add(new AttributeValue("Vegetable Cell", "", 0));
		resultList.add(new AttributeValue("Epithelial Cell", "", 0));
		resultList.add(new AttributeValue("Pus Cell", "", 0));
		resultList.add(new AttributeValue("RBC", "", 0));
		resultList.add(new AttributeValue("Muscle Fiber", "", 0));
		resultList.add(new AttributeValue("Fat", "", 0));
		resultList.add(new AttributeValue("Starch", "", 0));
		resultList.add(new AttributeValue("Ova", "", 0));
		resultList.add(new AttributeValue("Cyst", "", 0));
		resultList.add(new AttributeValue("Trophozoite", "", 0));


		//Attribute keyword list
		ArrayList<AttributeValueList> attributeKeywordArraylist = new ArrayList<>();
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.get(0).attributeKeywordList.add("consisten");
		attributeKeywordArraylist.get(0).attributeKeywordList.add("sisten");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("color");
		attributeKeywordArraylist.get(2).attributeKeywordList.add("mucus");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("blood");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("reaction");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("occult");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("cult blood");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("substance");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("ducing sub");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("vegetable");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("able cell");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("epithel");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("elial cell");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("pus");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("rbc");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("r.b.c");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("muscle");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("fiber");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("fibre");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("fat");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("starch");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("ova");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("cyst");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("trophozoite");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("phozoit");


		//Value keyword list
		ArrayList<String> valueKeywordList = new ArrayList<>();
		valueKeywordList.add("nil");
		valueKeywordList.add("formed");
		valueKeywordList.add("soft");
		valueKeywordList.add("loose");
		valueKeywordList.add("watery");
		valueKeywordList.add("brown");
		valueKeywordList.add("black");
		valueKeywordList.add("green");
		valueKeywordList.add("yellow");
		valueKeywordList.add("red");
		valueKeywordList.add("dark");
		valueKeywordList.add("light");
		valueKeywordList.add("(+");
		valueKeywordList.add("+)");
		valueKeywordList.add("(-");
		valueKeywordList.add("-)");
		valueKeywordList.add("acid");
		valueKeywordList.add("normal");
		valueKeywordList.add("alkal");
		valueKeywordList.add("want");

		//Now go home boy!
		return rearrangeResult(scannedLineList, attributeKeywordArraylist, valueKeywordList, resultList, "STOOL");
	}

	public ArrayList<AttributeValue> getStoolResultRegex(ArrayList<String> scannedLineList)
	{
		ArrayList<AttributeValue> resultList = new ArrayList<>();
		resultList.add(new AttributeValue("Consistency", "", 0));
		resultList.add(new AttributeValue("Color", "", 0));
		resultList.add(new AttributeValue("Mucus", "", 0));
		resultList.add(new AttributeValue("Blood", "", 0));
		resultList.add(new AttributeValue("Reaction", "", 0));
		resultList.add(new AttributeValue("Occult Blood Test", "", 0));
		resultList.add(new AttributeValue("Reducing Substance", "", 0));
		resultList.add(new AttributeValue("Vegetable Cell", "", 0));
		resultList.add(new AttributeValue("Epithelial Cell", "", 0));
		resultList.add(new AttributeValue("Pus Cell", "", 0));
		resultList.add(new AttributeValue("RBC", "", 0));
		resultList.add(new AttributeValue("Muscle Fiber", "", 0));
		resultList.add(new AttributeValue("Fat", "", 0));
		resultList.add(new AttributeValue("Starch", "", 0));
		resultList.add(new AttributeValue("Ova", "", 0));
		resultList.add(new AttributeValue("Cyst", "", 0));
		resultList.add(new AttributeValue("Trophozoite", "", 0));


		//Attribute keyword list
		ArrayList<AttributeValueList> attributeKeywordArraylist = new ArrayList<>();
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.get(0).attributeKeywordList.add("consisten");
		attributeKeywordArraylist.get(0).attributeKeywordList.add("sisten");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("color");
		attributeKeywordArraylist.get(2).attributeKeywordList.add("mucus");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("blood");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("reaction");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("occult");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("cult blood");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("substance");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("ducing sub");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("vegetable");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("able cell");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("epithel");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("elial cell");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("pus");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("rbc");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("r.b.c");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("muscle");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("fiber");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("fibre");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("fat");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("starch");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("ova");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("cyst");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("trophozoite");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("phozoit");


		//Value keyword list
		ArrayList<String> valueKeywordList = new ArrayList<>();
		valueKeywordList.add("nil");
		valueKeywordList.add("formed");
		valueKeywordList.add("soft");
		valueKeywordList.add("loose");
		valueKeywordList.add("watery");
		valueKeywordList.add("brown");
		valueKeywordList.add("black");
		valueKeywordList.add("green");
		valueKeywordList.add("yellow");
		valueKeywordList.add("red");
		valueKeywordList.add("dark");
		valueKeywordList.add("light");
		valueKeywordList.add("(+");
		valueKeywordList.add("+)");
		valueKeywordList.add("(-");
		valueKeywordList.add("-)");
		valueKeywordList.add("acid");
		valueKeywordList.add("normal");
		valueKeywordList.add("alkal");
		valueKeywordList.add("want");

		//Now go home boy!
		return rearrangeResult(scannedLineList, attributeKeywordArraylist, valueKeywordList, resultList, "STOOLR");
	}

	public ArrayList<AttributeValue> getCBCResult(ArrayList<String> scannedLineList)
	{
		ArrayList<AttributeValue> resultList = new ArrayList<>();
		resultList.add(new AttributeValue("HGB (Haemoglobin)", "", 0));
		resultList.add(new AttributeValue("(Cyn meth method)", "", 0));
		resultList.add(new AttributeValue("ESR (Westergreen Method)", "", 0));
		resultList.add(new AttributeValue("Total RBC Count", "", 0));
		resultList.add(new AttributeValue("HCT (Haematocrit)", "", 0));
		resultList.add(new AttributeValue("M.C.V.", "", 0));
		resultList.add(new AttributeValue("MCH", "", 0));
		resultList.add(new AttributeValue("MCHC", "", 0));
		resultList.add(new AttributeValue("RDW-CV", "", 0));
		resultList.add(new AttributeValue("PDW", "", 0));
		resultList.add(new AttributeValue("MPV", "", 0));
		resultList.add(new AttributeValue("P-LCR", "", 0));
		resultList.add(new AttributeValue("Platelets Count", "", 0));
		resultList.add(new AttributeValue("Total WBC Count", "", 0));
		resultList.add(new AttributeValue("Neutrophils", "", 0));
		resultList.add(new AttributeValue("Lymphocytes", "", 0));
		resultList.add(new AttributeValue("Eosinophils", "", 0));
		resultList.add(new AttributeValue("Basophils", "", 0));
		resultList.add(new AttributeValue("Monocytes", "", 0));
		resultList.add(new AttributeValue("Blast", "", 0));
		resultList.add(new AttributeValue("Cir. Eosinophil Count", "", 0));
		resultList.add(new AttributeValue("MP (Malarial Parasite)", "", 0));


		//Attribute keyword list
		ArrayList<AttributeValueList> attributeKeywordArraylist = new ArrayList<>();
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.add(new AttributeValueList());
		attributeKeywordArraylist.get(0).attributeKeywordList.add("hgb");
		attributeKeywordArraylist.get(0).attributeKeywordList.add("haemoglo");
		attributeKeywordArraylist.get(0).attributeKeywordList.add("hemoglo");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("cyn");
		attributeKeywordArraylist.get(1).attributeKeywordList.add("meth method");
		attributeKeywordArraylist.get(2).attributeKeywordList.add("esr");
		attributeKeywordArraylist.get(2).attributeKeywordList.add("westergreen");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("rbc");
		attributeKeywordArraylist.get(3).attributeKeywordList.add("r.b.c");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("hct");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("h.c.t");
		attributeKeywordArraylist.get(4).attributeKeywordList.add("haematrocrit");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("m.c.v");
		attributeKeywordArraylist.get(5).attributeKeywordList.add("mcv");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("mch");
		attributeKeywordArraylist.get(6).attributeKeywordList.add("m.c.h");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("mchc");
		attributeKeywordArraylist.get(7).attributeKeywordList.add("m.c.h.c");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("rdw");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("r.d.w");
		attributeKeywordArraylist.get(8).attributeKeywordList.add("dw-cv");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("pdw");
		attributeKeywordArraylist.get(9).attributeKeywordList.add("p.d.w");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("mpv");
		attributeKeywordArraylist.get(10).attributeKeywordList.add("m.p.v");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("p-lc");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("lcr");
		attributeKeywordArraylist.get(11).attributeKeywordList.add("l.c.r");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("platelet");
		attributeKeywordArraylist.get(12).attributeKeywordList.add("lets count");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("total wbc");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("w.b.c");
		attributeKeywordArraylist.get(13).attributeKeywordList.add("wbc");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("neutrophil");
		attributeKeywordArraylist.get(14).attributeKeywordList.add("trophils");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("lymphocyte");
		attributeKeywordArraylist.get(15).attributeKeywordList.add("phocytes");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("eosinophil");
		attributeKeywordArraylist.get(16).attributeKeywordList.add("sinophils");
		attributeKeywordArraylist.get(17).attributeKeywordList.add("basophil");
		attributeKeywordArraylist.get(18).attributeKeywordList.add("monocyte");
		attributeKeywordArraylist.get(19).attributeKeywordList.add("blast");
		attributeKeywordArraylist.get(20).attributeKeywordList.add("cir");
		attributeKeywordArraylist.get(20).attributeKeywordList.add("cir esonophil");
		attributeKeywordArraylist.get(21).attributeKeywordList.add("malarial");
		attributeKeywordArraylist.get(21).attributeKeywordList.add("parasite");


		//Value keyword list
		ArrayList<String> valueKeywordList = new ArrayList<>();
		valueKeywordList.add("%");
		valueKeywordList.add("dl");
		valueKeywordList.add("g/d");
		valueKeywordList.add("/d");
		valueKeywordList.add("in 1");
		valueKeywordList.add("fl");
		valueKeywordList.add("hr");
		valueKeywordList.add("cm");
		valueKeywordList.add("/cm");
		valueKeywordList.add("pg");
		valueKeywordList.add("/u");
		valueKeywordList.add("ul");
		valueKeywordList.add("/μ");
		valueKeywordList.add("μl");
		valueKeywordList.add("/l");
		valueKeywordList.add("fi");
		valueKeywordList.add("di");
		valueKeywordList.add("f1");

		//Now go home boy!
		return rearrangeResult(scannedLineList, attributeKeywordArraylist, valueKeywordList, resultList, "CBC");
	}


	private ArrayList<AttributeValue> rearrangeResult(ArrayList<String> scannedLineList, ArrayList<AttributeValueList> attributeKeywordArraylist, ArrayList<String> valueKeywordList, ArrayList<AttributeValue> resultList, String reportType)
	{
		//Find out appropriate attribite and values from scannedLineList
		ArrayList<String> attributeList = new ArrayList<>();
		ArrayList<String> valueList = new ArrayList<>();
		for (i = 0; i < scannedLineList.size(); i++)
		{
			//collecting and storing attribute into attributeList from scannedLineList
			for (j = 0; j < attributeKeywordArraylist.size(); j++)
			{
				for (k = 0; k < attributeKeywordArraylist.get(j).attributeKeywordList.size(); k++)
				{
					if (scannedLineList.get(i).toLowerCase().contains(attributeKeywordArraylist.get(j).attributeKeywordList.get(k)))
					{
						attributeList.add(resultList.get(j).getAttribute());
						break;
					}
				}
			}

			//collecting and storing value into valueList from scannedLineList
			for (j = 0; j < valueKeywordList.size(); j++)
			{
				if (scannedLineList.get(i).toLowerCase().contains(valueKeywordList.get(j)))
				{
					if (!scannedLineList.get(i).toLowerCase().contains("differen") && !scannedLineList.get(i).toLowerCase().contains("reduc"))
					{
						valueList.add(scannedLineList.get(i));
						break;
					}
				}
			}
		}


		//Now fetch attribute, value and accuracy properly
		int attributeValueListMinimumSize = attributeList.size();
		if (attributeValueListMinimumSize > valueList.size())
		{
			attributeValueListMinimumSize = valueList.size();
		}
		for (i = 0; i < resultList.size(); i++)
		{
			for (j = 0; j < attributeValueListMinimumSize; j++)
			{
				if (resultList.get(i).getAttribute().equals(attributeList.get(j)))
				{
					resultList.get(i).setValue(valueList.get(j));
					resultList.get(i).setAccuracy(1);
					break;
				}
			}
		}

		if (reportType.equals("STOOL"))
		{
			return validateAndRearrangeStoolValue(resultList);
		}
		if (reportType.equals("STOOLR"))
		{
			return validateAndRearrangeStoolValueRegex(resultList);
		}
		if (reportType.equals("URINE"))
		{
			return validateAndRearrangeUrineValue(resultList);
		}
		if (reportType.equals("CBC"))
		{
			return validateAndRearrangeCBCValue(resultList);
		}

		return resultList;
	}


	private ArrayList<AttributeValue> validateAndRearrangeStoolValue(ArrayList<AttributeValue> resultList)
	{
		ArrayList<String> generalValueOptions = new ArrayList<>();
		generalValueOptions.add("+");
		generalValueOptions.add("-");
		generalValueOptions.add("nil");

		ArrayList<AttributeValueList> valueKeywordArraylist = new ArrayList<>();
		for (i = 0; i < 17; i++)
		{
			valueKeywordArraylist.add(new AttributeValueList());
		}
		valueKeywordArraylist.get(0).valueKeywordList.add("formed");
		valueKeywordArraylist.get(0).valueKeywordList.add("soft");
		valueKeywordArraylist.get(0).valueKeywordList.add("loose");
		valueKeywordArraylist.get(0).valueKeywordList.add("watery");
		valueKeywordArraylist.get(1).valueKeywordList.add("brown");
		valueKeywordArraylist.get(1).valueKeywordList.add("black");
		valueKeywordArraylist.get(1).valueKeywordList.add("green");
		valueKeywordArraylist.get(1).valueKeywordList.add("yellow");
		valueKeywordArraylist.get(1).valueKeywordList.add("red");
		valueKeywordArraylist.get(1).valueKeywordList.add("light");
		valueKeywordArraylist.get(1).valueKeywordList.add("dark");
		i = 2;
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(4).valueKeywordList.add("normal");
		valueKeywordArraylist.get(4).valueKeywordList.add("acidic");
		valueKeywordArraylist.get(4).valueKeywordList.add("Alkaline");
		valueKeywordArraylist.get(5).valueKeywordList.add("want");
		valueKeywordArraylist.get(6).valueKeywordList.add("want");
		i = 7;
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);

		return validateOCRResult(valueKeywordArraylist, resultList);
	}


	private ArrayList<AttributeValue> validateAndRearrangeStoolValueRegex(ArrayList<AttributeValue> resultList)
	{
		ArrayList<String> generalValueOptions = new ArrayList<>();
		generalValueOptions.add("[\\s]*\\(?[\\s]*[\\+\\s]{1,5}[\\s]*\\)?[\\s]*");
		generalValueOptions.add("[\\s]*\\(?[\\s]*[\\-\\s]{1,5}[\\s]*\\)?[\\s]*");
		generalValueOptions.add("[\\w\\s]*(?i)(nil)[\\w\\s]*");

		ArrayList<AttributeValueList> valueKeywordArraylist = new ArrayList<>();
		for (i = 0; i < 17; i++)
		{
			valueKeywordArraylist.add(new AttributeValueList());
		}
		valueKeywordArraylist.get(0).valueKeywordList.add("[\\w\\s]*(?i)(formed|soft|loose|watery)[\\w\\s]*");
		valueKeywordArraylist.get(1).valueKeywordList.add("[\\w\\s]*(?i)(brown|black|green|yellow|red|light|dark)[\\w\\s]*");
		i = 2;
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(1).valueKeywordList.add("[\\w\\s]*(?i)(normal|acidic|alkaline)[\\w\\s]*");
		valueKeywordArraylist.get(5).valueKeywordList.add("[\\w\\s]*(?i)(want)[\\w\\s]*");
		valueKeywordArraylist.get(6).valueKeywordList.add("[\\w\\s]*(?i)(want)[\\w\\s]*");
		i = 7;
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);

		return validateOCRResultUsingRegex(valueKeywordArraylist, resultList);
	}


	private ArrayList<AttributeValue> validateAndRearrangeUrineValue(ArrayList<AttributeValue> resultList)
	{
		ArrayList<String> generalValueOptions = new ArrayList<>();
		generalValueOptions.add("+");
		generalValueOptions.add("-");
		generalValueOptions.add("nil");

		ArrayList<AttributeValueList> valueKeywordArraylist = new ArrayList<>();
		for (i = 0; i < 19; i++)
		{
			valueKeywordArraylist.add(new AttributeValueList());
		}
		valueKeywordArraylist.get(0).valueKeywordList.add("straw");
		valueKeywordArraylist.get(0).valueKeywordList.add("yellow");
		valueKeywordArraylist.get(0).valueKeywordList.add("dark");
		valueKeywordArraylist.get(0).valueKeywordList.add("light");
		valueKeywordArraylist.get(0).valueKeywordList.add("amber");
		valueKeywordArraylist.get(0).valueKeywordList.add("orange");
		valueKeywordArraylist.get(0).valueKeywordList.add("brown");
		valueKeywordArraylist.get(0).valueKeywordList.add("red");
		valueKeywordArraylist.get(0).valueKeywordList.add("white");
		valueKeywordArraylist.get(0).valueKeywordList.add("green");
		valueKeywordArraylist.get(0).valueKeywordList.add("blue");
		valueKeywordArraylist.get(0).valueKeywordList.add("red");
		valueKeywordArraylist.get(1).valueKeywordList.add("clear");
		valueKeywordArraylist.get(1).valueKeywordList.add("cloudy");
		valueKeywordArraylist.get(2).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(3).valueKeywordList.add("qns");
		valueKeywordArraylist.get(3).valueKeywordList.add("q.n.s");
		valueKeywordArraylist.get(4).valueKeywordList.add("acidic");
		valueKeywordArraylist.get(4).valueKeywordList.add("basic");
		valueKeywordArraylist.get(4).valueKeywordList.add("alkaline");
		valueKeywordArraylist.get(5).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(6).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(7).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(8).valueKeywordList.add("hpf");
		valueKeywordArraylist.get(8).valueKeywordList.add("/hp");
		valueKeywordArraylist.get(9).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(10).valueKeywordList.add("hpf");
		valueKeywordArraylist.get(10).valueKeywordList.add("/hp");
		i = 11;
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);
		valueKeywordArraylist.get(i++).valueKeywordList.addAll(generalValueOptions);

		return validateOCRResult(valueKeywordArraylist, resultList);
	}


	private ArrayList<AttributeValue> validateAndRearrangeCBCValue(ArrayList<AttributeValue> resultList)
	{
		ArrayList<String> percentageValueOptions = new ArrayList<>();
		percentageValueOptions.add("[\\d.\\s]*%[\\s]*");

		ArrayList<String> gdlValueOptions = new ArrayList<>();
		gdlValueOptions.add("[\\d.\\s]{1,4}[g][/][d][Ll1i]?[\\s]*");

		ArrayList<String> flValueOptions = new ArrayList<>();
		flValueOptions.add("[\\d.\\s]{1,4}[f][Ll1i]?[\\s]*");

		ArrayList<String> esrValueOptions = new ArrayList<>();
		esrValueOptions.add("[\\d.\\s]{1,5}[\\s][\\w\\d\\s]*[hH][\\w]*[r][^|]*");

		ArrayList<String> ulValueOptions = new ArrayList<>();
		ulValueOptions.add("[\\d.\\s]{1,5}[/][uμ][Ll1i]?[\\s]*");

		ArrayList<String> ulTotalWBCValueOptions = new ArrayList<>();
		ulTotalWBCValueOptions.add("[\\d.\\s]{3,7}[/][uμ][Ll1i]?[\\s]*");

		ArrayList<AttributeValueList> valueKeywordArraylist = new ArrayList<>();
		for (i = 0; i < 22; i++)
		{
			valueKeywordArraylist.add(new AttributeValueList());
		}
		valueKeywordArraylist.get(0).valueKeywordList.addAll(gdlValueOptions);
		valueKeywordArraylist.get(1).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(2).valueKeywordList.addAll(esrValueOptions);
		valueKeywordArraylist.get(3).valueKeywordList.add("[\\d.,\\s]*million[\\w\\s/μ]*");
		valueKeywordArraylist.get(4).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(5).valueKeywordList.addAll(flValueOptions);
		valueKeywordArraylist.get(6).valueKeywordList.add("[\\d.,\\s]*pg[\\s]*");
		valueKeywordArraylist.get(7).valueKeywordList.addAll(gdlValueOptions);
		valueKeywordArraylist.get(8).valueKeywordList.addAll(flValueOptions);
		valueKeywordArraylist.get(9).valueKeywordList.addAll(flValueOptions);
		valueKeywordArraylist.get(10).valueKeywordList.addAll(flValueOptions);
		valueKeywordArraylist.get(11).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(12).valueKeywordList.add("[\\d.,\\s]{5,10}/cm[\\w\\s]*");
		valueKeywordArraylist.get(13).valueKeywordList.addAll(ulTotalWBCValueOptions);
		valueKeywordArraylist.get(14).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(15).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(16).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(17).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(18).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(19).valueKeywordList.addAll(percentageValueOptions);
		valueKeywordArraylist.get(20).valueKeywordList.add("[\\d.,\\s]{1,4}/cm[\\w\\s]*");
		valueKeywordArraylist.get(21).valueKeywordList.addAll(percentageValueOptions);

		return validateOCRResultUsingRegex(valueKeywordArraylist, resultList);
	}


	private ArrayList<AttributeValue> validateOCRResultUsingRegex(ArrayList<AttributeValueList> valueKeywordArraylist, ArrayList<AttributeValue> resultList)
	{
		//Checking if the OCR has detected the right value or not
		String tempString = "";
		int tempIndex, flag = 0;
		for (i = 0; i < resultList.size() - 1; i++)
		{
			flag = 0;
			for (k = 0; k < valueKeywordArraylist.get(i).valueKeywordList.size(); k++)
			{
				if (resultList.get(i).getValue().matches(valueKeywordArraylist.get(i).valueKeywordList.get(k)))
				{
					flag = 1;
					break;
				}
			}
			if (flag == 0) //That means OCR could not fetch the right value, now find and fetch right value
			{
				for (j = i + 1; j < resultList.size(); j++)
				{
					tempIndex = -1;
					//Finding right value within rest of the results(j=i+1)
					for (k = 0; k < valueKeywordArraylist.get(i).valueKeywordList.size(); k++)
					{
						if (resultList.get(j).getValue().matches(valueKeywordArraylist.get(i).valueKeywordList.get(k)))
						{
							tempIndex = j;
							break;
						}
					}
					if (tempIndex > -1) //That means an appropiate value found, now swaping the value.
					{
						tempString = resultList.get(i).getValue();
						resultList.get(i).setValue(resultList.get(tempIndex).getValue());
						resultList.get(tempIndex).setValue(tempString);
						resultList.get(i).setAccuracy(0.8);
						break;
					}
					else
					{
						resultList.get(i).setAccuracy(0);
					}
				}
			}
			else
			{
				resultList.get(i).setAccuracy(1);
			}

		}


		return resultList;
	}


	private ArrayList<AttributeValue> validateOCRResult(ArrayList<AttributeValueList> valueKeywordArraylist, ArrayList<AttributeValue> resultList)
	{
		//Checking if the OCR has detected the right value or not
		String tempString = "";
		int tempIndex, flag = 0;
		for (i = 0; i < resultList.size() - 1; i++)
		{
			flag = 0;
			for (k = 0; k < valueKeywordArraylist.get(i).valueKeywordList.size(); k++)
			{
				if (resultList.get(i).getValue().toLowerCase().contains(valueKeywordArraylist.get(i).valueKeywordList.get(k)))
				{
					flag = 1;
					break;
				}
			}
			if (flag == 0)
			{
				for (j = i + 1; j < resultList.size(); j++)
				{
					tempIndex = -1;
					for (k = 0; k < valueKeywordArraylist.get(i).valueKeywordList.size(); k++)
					{
						if (resultList.get(j).getValue().toLowerCase().contains(valueKeywordArraylist.get(i).valueKeywordList.get(k)))
						{
							tempIndex = j;
							break;
						}
					}
					if (tempIndex > -1)
					{
						tempString = resultList.get(i).getValue();
						resultList.get(i).setValue(resultList.get(tempIndex).getValue());
						resultList.get(tempIndex).setValue(tempString);
						resultList.get(i).setAccuracy(0.8);
						break;
					}
					else
					{
						resultList.get(i).setAccuracy(0);
					}
				}
			}
			else
			{
				resultList.get(i).setAccuracy(1);
			}

		}


		return resultList;
	}

	private String getDecimalValueFromString(String str)
	{
		return str.replaceAll("[^0-9?!\\.!\\-]", "");
	}


}
