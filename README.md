# SBAS-L13-View-TicTacToe

TicTacToeView extends GridView 

анимация появлнеия поля, хода, победной комбинации ("-" часть в customView, часть в адаптере, в MainActivity)
сохранение поля и результата при повороте ("-"!при победе), сохранение в MainActivity (!в customView, 
т.к. java.lang.ClassCastException: com.rdi.tictactoe.TicTacToeView$SavedState cannot be cast to android.widget.AbsListView$SavedState)
