package com.tjyw.atom.pub.interfaces;

import android.content.Context;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.tjyw.atom.network.utils.ArrayUtil;

import java.util.List;

/**
 * Created by stephen on 28/04/2017.
 */
public abstract class AtomPubValidationListener implements Validator.ValidationListener {

    protected boolean showToast;

    protected Context context;

    public AtomPubValidationListener(Context context) {
        this(context, true);
    }

    public AtomPubValidationListener(Context context, boolean showToast) {
        this.context = context;
        this.showToast = showToast;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (showToast && ! ArrayUtil.isEmpty(errors)) {
            ValidationError error = errors.get(0);
            if (null != error && ! ArrayUtil.isEmpty(error.getFailedRules())) {
                Rule rule = error.getFailedRules().get(0);
                if (null != rule) {
                    Toast.makeText(context, rule.getMessage(context), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
