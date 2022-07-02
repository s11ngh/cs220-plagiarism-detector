package plagdetect;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class PlagiarismDetector implements IPlagiarismDetector {
	int sizeNgram;
	//store ngrams
	Map<String, HashSet<String>> ngram;
	//answer to compare them
	Map<String, HashSet<ArrayList<String>>> answer;
	
	public PlagiarismDetector(int n) {
		// TODO implement this method
		sizeNgram=n;
	}
	
	@Override
	public int getN() {
		// TODO Auto-generated method stub
		return sizeNgram;
	}

	@Override
	public Collection<String> getFilenames() {
		// TODO Auto-generated method stub
		return ngram.keySet();
	}

	@Override
	public Collection<String> getNgramsInFile(String filename) {
		// TODO Auto-generated method stub
		return ngram.get(filename);
	}

	@Override
	public int getNumNgramsInFile(String filename) {
		// TODO Auto-generated method stub
		return ngram.get(filename).size();
	}

	@Override
	public Map<String, Map<String, Integer>> getResults() {
		// TODO Auto-generated method stub
		return answer;
	}

	@Override
	public void readFile(File file) throws IOException {
		// TODO Auto-generated method stub
		// most of your work can happen in this method
		Map<String, Integer> map = new HashMap<>();
		for(String gram: ngram.keySet()){
			Set<String> add = new HashSet<>();
			add = ngram.get(gram);
			add.retainAll(ngram.getName());
			map.put(gram, add.size());
		}
		answer.put(file.getName(), map);
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		// TODO Auto-generated method stub
		if(!answer.containsKey(file1) || !answer.containsKey(file2)){
			return 0;
		} else{return answer.get(file1).get(file2);}
	}

	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		// TODO Auto-generated method stub
		Collection<String> gram1 = answer.keySet();
		Collection<String> gram2 = gram1;
		Set<String> alleged = new HashSet<>();
		for(String s: gram1){
			for(String t: gram2){
				if(answer.getNumGramsInCommon(s,t)>=minNgrams && 0 > s.compare()){
					alleged.add(s+" "+t+" "+getNumGramsInCommon(s,t));
				}
			}
		}
		return alleged;
	}

	@Override
	public void readFilesInDirectory(File dir) throws IOException {
		// delegation!
		// just go through each file in the directory, and delegate
		// to the method for reading a file
		for (File f : dir.listFiles()) {
			readFile(f);
		}
	}
}
