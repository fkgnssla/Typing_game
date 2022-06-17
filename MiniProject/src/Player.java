public class Player implements Comparable<Player>{
	private String name = null;	//플레이어의 아이디
	private int score;			//플레이어의 최종 점수
	
	Player(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	//닉네임 반환
	String getName() {
		return name;
	}
	
	//점수 반환
	int getScore() {
		return score;
	}
	
	//정렬 형식을 오버라이딩하여 재정의한다
	@Override
	public int compareTo(Player o) {
		if(this.score < o.score) {
			return 1;
		} else if(this.score == o.score) {
			return 0;
		} else {
			return -1;
		}
	}
}
