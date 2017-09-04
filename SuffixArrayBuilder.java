import java.util.*;
class SuffixArrayBuilder{
	static class Suffix implements Comparable<Suffix>{
		int index;
		int rank[] = new int[2];
		@Override
		public int compareTo(Suffix o1){
			if(rank[0] == o1.rank[0])
				return Integer.compare(rank[1],o1.rank[1]);
			else
				return Integer.compare(rank[0],o1.rank[0]);
		}
	}
	static int[] buildSuffixArray(String str){
		int n = str.length();
		Suffix suffixes[] = new Suffix[n];
		for(int i = 0;i<n;i++){
			suffixes[i] = new Suffix();
			suffixes[i].index = i;
			suffixes[i].rank[0] = str.charAt(i)-'a';
			suffixes[i].rank[1] = ((i+1)<n)?(str.charAt(i+1)-'a'):-1;
		}
		Arrays.sort(suffixes);
		
		int ind[] = new int[n];
		for(int k = 4;k<2*n;k*=2){
			int rank = 0;
			int prev_rank = suffixes[0].rank[0];
			suffixes[0].rank[0] = rank;
			ind[suffixes[0].index] = 0;
			for(int i = 1;i<n;i++){
				
				if(suffixes[i].rank[0] == prev_rank && suffixes[i].rank[1] == suffixes[i-1].rank[1]){
					prev_rank = suffixes[i].rank[0];
					suffixes[i].rank[0] = rank;
				}
				else{
					prev_rank = suffixes[i].rank[0];
					suffixes[i].rank[0] = ++rank;
				}
				
				ind[suffixes[i].index] = i;
			}
			for (int i = 0;i<n;i++) {
				int nextIndex = suffixes[i].index+k/2;
				suffixes[i].rank[1] = (nextIndex<n)?suffixes[ind[nextIndex]].rank[0]:-1;
			}
			Arrays.sort(suffixes);
		}
		int suffixArray[] = new int[n];
		for(int i = 0;i<n;i++)
			suffixArray[i] = suffixes[i].index;
		return suffixArray;
	}
	//driver method
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = in.nextLine().trim();
		int suffixArray[] = buildSuffixArray(str);
		System.out.println(Arrays.toString(suffixArray));
	}
}
