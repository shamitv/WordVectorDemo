package in.shamit.nlp.wordvec;

import in.shamit.nlp.wordvec.demo.services.vo.DistResponse;

public class EquationSolver {

	public DistResponse[] solveEuation(String eq){
		String operatorPrtn="((?<=[+-])|(?=[+-]))";
		String parts[]=eq.split(operatorPrtn);
		MathVector v=null;
		boolean isLastOpPlus=true;
		for(String p:parts){
			p=p.trim();
			System.out.println(p.trim());
			if(v==null){
				v=getVector(p);
			}else{
				if(p.equals("+")){
					isLastOpPlus=true;
				}else{
					if(p.equals("-")){
						isLastOpPlus=false;
					}else{
						MathVector v2=null;
						v2=getVector(p);
						if(v2!=null && v!=null){
							if(isLastOpPlus){
								v=v.plus(v2);
							}else{
								v=v.minus(v2);
							}
						}
					}
				}
			}
		}
		if(v==null){
			return null;
		}else{
			return VectorManager.getNearbyWords(v.getValues());
		}
	}
	
	private MathVector getVector(String token) {
		float vals[]=VectorManager.getVector(token);
		if(vals==null){
			return null;
		}else{
			MathVector v=new MathVector(vals); 
			return v;
		}
	}

	public static void main(String[] args) {
		EquationSolver solver=new EquationSolver();
		solver.solveEuation("Obama + Russia - USA");

	}

}
