public class HangPlayer 
{
	private String name;
	private int wins;
	private int loss;
	public HangPlayer(String n)
	{
		name = n;
	}
	public void setWins(int w)
	{
		wins = w;
	}
	public void setLosses(int l)
	{
		loss = l;
	}
	public int getWins()
	{
		return wins;
	}
	public int getLosses()
	{
		return loss;
	}
	public boolean equals(String n)
	{
		if(name.equalsIgnoreCase(n))
			return true;
		else
			return false;
	}
	public String toString()
	{
		StringBuilder S = new StringBuilder();
		S.append("\n" + name + "\n");
		S.append(wins + "\n");
		S.append(loss + "\n");
		return S.toString();
	}
	public String toStringAlso()
	{
	StringBuilder B = new StringBuilder();
	B.append(name + "\n" + wins + "\n" + loss + "\n");
	return B.toString();
	}
	
}