
public class A22 implements A22I {
	String[][] dependencies;
	public A22(String[][] pDependencies) {
		for(int i = 0; i<pDependencies.length; i++) {
			if(pDependencies[i].length!=2)
				System.out.println("Array hat nicht die richtige Größe.");
		}
		dependencies=pDependencies;
	} 
	
	@Override
	public boolean isWellSorted(String[] Sequence) {
		for(int i = 0; i<dependencies.length; i++) {
			for(int j=0; j<Sequence.length; j++) {
				if(dependencies[i][1] == Sequence[j])
					return false;
				if(dependencies[i][0] == Sequence[j])
					break;
			}
		}
		for(int i=0; i<Sequence.length; i++) {
			for(int j=i+1; j<Sequence.length; j++) {
				if (Sequence[i]==Sequence[j])
					return false;
			}
		}
		return true;
	}
}
