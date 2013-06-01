
public class Generator implements BoardGenerator {

	@Override
	public void generateBoard(Board board) {
		BoardFiller filler = new BoardFiller(board);
		Removal r = new Removal(board);
		filler.fillBoard();
		
		r.removeValues(new SimpleRemover(board));

		r.removeValues(new HardRemover(board));
		
		r.removeValues(new ExhaustiveRemover(board));
	}


}
