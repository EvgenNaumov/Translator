package geekbrains.ru.translator.utils.ui

import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import geekbrains.ru.translator.view.main.MainActivity

class AlertDialogFragment:AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val context = activity
        var alertDialog = getStubAlertDialog(context!!)
        val args = arguments
        if (args != null) {
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog

    }

    companion object {

        private const val TITLE_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        private const val MESSAGE_EXTRA = "0dd00b66-91c2-447d-b627-530065040905"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

}