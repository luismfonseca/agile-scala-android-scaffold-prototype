package pt.pimentelfonseca.agilescalaandroid.app

import org.scaloid.common._
import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater

class HelloScaloid extends SActivity {

  onCreate {
    /*contentView = new SVerticalLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
        case v => v.backgroundColor(Color.YELLOW)
      }

      STextView("I am 10 dip tall")
      STextView("Me too")
      STextView("I am taller than you") textSize 15.dip // overriding
      SEditText("Yellow input field")
      SButton(R.string.red)
    } padding 20.dip*/
	setContentView(R.layout.fragment_post);
  }
  
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    
	getMenuInflater().inflate(R.menu.main_post, menu)
	return true
	
	//return super.onCreateOptionsMenu(menu)
  }

}
