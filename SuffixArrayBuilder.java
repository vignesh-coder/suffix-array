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
		Suffix suffices[] = new Suffix[n];
		for(int i = 0;i<n;i++){
			suffices[i] = new Suffix();
			suffices[i].index = i;
			suffices[i].rank[0] = str.charAt(i)-'a';
			suffices[i].rank[1] = ((i+1)<n)?(str.charAt(i+1)-'a'):-1;
		}
		Arrays.sort(suffices);
		
		int ind[] = new int[n];
		for(int k = 4;k<2*n;k*=2){
			int rank = 0;
			int prev_rank = suffices[0].rank[0];
			suffices[0].rank[0] = rank;
			ind[suffices[0].index] = 0;
			for(int i = 1;i<n;i++){
				
				if(suffices[i].rank[0] == prev_rank && suffices[i].rank[1] == suffices[i-1].rank[1]){
					prev_rank = suffices[i].rank[0];
					suffices[i].rank[0] = rank;
				}
				else{
					prev_rank = suffices[i].rank[0];
					suffices[i].rank[0] = ++rank;
				}
				
				ind[suffices[i].index] = i;
			}
			for (int i = 0;i<n;i++) {
				int nextIndex = suffices[i].index+k/2;
				suffices[i].rank[1] = (nextIndex<n)?suffices[ind[nextIndex]].rank[0]:-1;
			}
			Arrays.sort(suffices);
		}
		int suffixArray[] = new int[n];
		for(int i = 0;i<n;i++)
			suffixArray[i] = suffices[i].index;
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
