/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.classmethod.android.sample.customtimepicker;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

/**
 * A view for selecting the time of day, in either 24 hour or AM/PM mode. The
 * hour, each minute digit, and AM/PM (if applicable) can be conrolled by
 * vertical spinners. The hour can be entered by keyboard input. Entering in two
 * digit hours can be accomplished by hitting two digits within a timeout of
 * about a second (e.g. '1' then '2' to select 12). The minutes can be entered
 * by entering single digits. Under AM/PM mode, the user can hit 'a', 'A", 'p'
 * or 'P' to pick. For a dialog using this view, see
 * {@link android.app.TimePickerDialog}.
 *<p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class TimePicker extends FrameLayout {

    private static final boolean DEFAULT_ENABLED_STATE = true;

    private static final int HOURS_IN_HALF_DAY = 12;

    /**
     * A no-op callback used in the constructor to avoid null checks later in
     * the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        }
    };
    
    private Context mContext;

    // state
    private boolean mIs24HourView;

    private boolean mIsAm;

    // ui components
    private final NumberPicker mHourSpinner;

    private final NumberPicker mMinuteSpinner;

    private final NumberPicker mAmPmSpinner;

    private final EditText mHourSpinnerInput;

    private final EditText mMinuteSpinnerInput;

    private final EditText mAmPmSpinnerInput;

    private final TextView mDivider;

    // Note that the legacy implementation of the TimePicker is
    // using a button for toggling between AM/PM while the new
    // version uses a NumberPicker spinner. Therefore the code
    // accommodates these two cases to be backwards compatible.
    private final Button mAmPmButton;

    private final String[] mAmPmStrings;

    private boolean mIsEnabled = DEFAULT_ENABLED_STATE;

    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    private Calendar mTempCalendar;

    private Locale mCurrentLocale;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The current hour.
         * @param minute The current minute.
         */
        void onTimeChanged(TimePicker view, int hourOfDay, int minute);
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, TimePicker.getInternalResrouce("timePickerStyle", "attr"));
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        this.mContext = context;

        // initialization based on locale
        setCurrentLocale(Locale.getDefault());

        // process style attributes
        int[] timePickerStyles = TimePicker.getInternalResrouces("TimePicker", "styleable", "com.android.internal");
        TypedArray attributesArray = context.obtainStyledAttributes(
                attrs, timePickerStyles, defStyle, 0);
        
        int timePickerStylesIL = TimePicker.getInternalResrouce("TimePicker_internalLayout", "styleable");
        int timePickerLayout = TimePicker.getInternalResrouce("time_picker", "layout");
        int layoutResourceId = attributesArray.getResourceId(
                timePickerStylesIL, timePickerLayout);
        attributesArray.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResourceId, this, true);

        // hour
        int hour = TimePicker.getInternalResrouce("hour", "id");
        mHourSpinner = (NumberPicker) findViewById(hour);
        mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                updateInputState();
                if (!is24HourView()) {
                    if ((oldVal == HOURS_IN_HALF_DAY - 1 && newVal == HOURS_IN_HALF_DAY)
                            || (oldVal == HOURS_IN_HALF_DAY && newVal == HOURS_IN_HALF_DAY - 1)) {
                        mIsAm = !mIsAm;
                        updateAmPmControl();
                    }
                }
                onTimeChanged();
            }
        });
        
        int numberPickerId = TimePicker.getInternalResrouce("numberpicker_input", "id");
        mHourSpinnerInput = (EditText) mHourSpinner.findViewById(numberPickerId);
        mHourSpinnerInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        // divider (only for the new widget style)
        int driverId = TimePicker.getInternalResrouce("divider", "id");
        mDivider = (TextView) findViewById(driverId);
        if (mDivider != null) {
        	int timePickerSepId = TimePicker.getInternalResrouce("time_picker_separator", "string");
            mDivider.setText(timePickerSepId);
        }

        // minute
        int minuteId = TimePicker.getInternalResrouce("minute", "id");
        mMinuteSpinner = (NumberPicker) findViewById(minuteId);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setMaxValue(59);
        mMinuteSpinner.setOnLongPressUpdateInterval(100);
        mMinuteSpinner.setFormatter(new TwoDigitFormatter());
        mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                updateInputState();
                int minValue = mMinuteSpinner.getMinValue();
                int maxValue = mMinuteSpinner.getMaxValue();
                if (oldVal == maxValue && newVal == minValue) {
                    int newHour = mHourSpinner.getValue() + 1;
                    if (!is24HourView() && newHour == HOURS_IN_HALF_DAY) {
                        mIsAm = !mIsAm;
                        updateAmPmControl();
                    }
                    mHourSpinner.setValue(newHour);
                } else if (oldVal == minValue && newVal == maxValue) {
                    int newHour = mHourSpinner.getValue() - 1;
                    if (!is24HourView() && newHour == HOURS_IN_HALF_DAY - 1) {
                        mIsAm = !mIsAm;
                        updateAmPmControl();
                    }
                    mHourSpinner.setValue(newHour);
                }
                onTimeChanged();
            }
        });
        
        int numberPickerInputId = TimePicker.getInternalResrouce("numberpicker_input", "id");
        mMinuteSpinnerInput = (EditText) mMinuteSpinner.findViewById(numberPickerInputId);
        mMinuteSpinnerInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        /* Get the localized am/pm strings and use them in the spinner */
        mAmPmStrings = new DateFormatSymbols().getAmPmStrings();

        // am/pm
        int amPmId = TimePicker.getInternalResrouce("amPm", "id");
        View amPmView = findViewById(amPmId);
        if (amPmView instanceof Button) {
            mAmPmSpinner = null;
            mAmPmSpinnerInput = null;
            mAmPmButton = (Button) amPmView;
            mAmPmButton.setOnClickListener(new OnClickListener() {
                public void onClick(View button) {
                    button.requestFocus();
                    mIsAm = !mIsAm;
                    updateAmPmControl();
                    onTimeChanged();
                }
            });
        } else {
            mAmPmButton = null;
            mAmPmSpinner = (NumberPicker) amPmView;
            mAmPmSpinner.setMinValue(0);
            mAmPmSpinner.setMaxValue(1);
            mAmPmSpinner.setDisplayedValues(mAmPmStrings);
            mAmPmSpinner.setOnValueChangedListener(new OnValueChangeListener() {
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    updateInputState();
                    picker.requestFocus();
                    mIsAm = !mIsAm;
                    updateAmPmControl();
                    onTimeChanged();
                }
            });
            
            mAmPmSpinnerInput = (EditText) mAmPmSpinner.findViewById(numberPickerInputId);
            mAmPmSpinnerInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }

        // update controls to initial state
        updateHourControl();
        updateAmPmControl();

        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        // set to current time
        setCurrentHour(mTempCalendar.get(Calendar.HOUR_OF_DAY));
        setCurrentMinute(mTempCalendar.get(Calendar.MINUTE));

        if (!isEnabled()) {
            setEnabled(false);
        }

        // set the content descriptions
        setContentDescriptions();

        // If not explicitly specified this view is important for accessibility.
//        if (getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
//            setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
//        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mIsEnabled == enabled) {
            return;
        }
        super.setEnabled(enabled);
        mMinuteSpinner.setEnabled(enabled);
        if (mDivider != null) {
            mDivider.setEnabled(enabled);
        }
        mHourSpinner.setEnabled(enabled);
        if (mAmPmSpinner != null) {
            mAmPmSpinner.setEnabled(enabled);
        } else {
            mAmPmButton.setEnabled(enabled);
        }
        mIsEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setCurrentLocale(newConfig.locale);
    }

    /**
     * Sets the current locale.
     *
     * @param locale The current locale.
     */
    private void setCurrentLocale(Locale locale) {
        if (locale.equals(mCurrentLocale)) {
            return;
        }
        mCurrentLocale = locale;
        mTempCalendar = Calendar.getInstance(locale);
    }

    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int mHour;

        private final int mMinute;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            mHour = hour;
            mMinute = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            mHour = in.readInt();
            mMinute = in.readInt();
        }

        public int getHour() {
            return mHour;
        }

        public int getMinute() {
            return mMinute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mHour);
            dest.writeInt(mMinute);
        }

        @SuppressWarnings({"unused", "hiding"})
        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getCurrentHour(), getCurrentMinute());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentHour(ss.getHour());
        setCurrentMinute(ss.getMinute());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     *
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The current hour in the range (0-23).
     */
    public Integer getCurrentHour() {
        int currentHour = mHourSpinner.getValue();
        if (is24HourView()) {
            return currentHour;
        } else if (mIsAm) {
            return currentHour % HOURS_IN_HALF_DAY;
        } else {
            return (currentHour % HOURS_IN_HALF_DAY) + HOURS_IN_HALF_DAY;
        }
    }

    /**
     * Set the current hour.
     */
    public void setCurrentHour(Integer currentHour) {
        // why was Integer used in the first place?
        if (currentHour == null || currentHour == getCurrentHour()) {
            return;
        }
        if (!is24HourView()) {
            // convert [0,23] ordinal to wall clock display
            if (currentHour >= HOURS_IN_HALF_DAY) {
                mIsAm = false;
                if (currentHour > HOURS_IN_HALF_DAY) {
                    currentHour = currentHour - HOURS_IN_HALF_DAY;
                }
            } else {
                mIsAm = true;
                if (currentHour == 0) {
                    currentHour = HOURS_IN_HALF_DAY;
                }
            }
            updateAmPmControl();
        }
        mHourSpinner.setValue(currentHour);
        onTimeChanged();
    }

    /**
     * Set whether in 24 hour or AM/PM mode.
     *
     * @param is24HourView True = 24 hour mode. False = AM/PM.
     */
    public void setIs24HourView(Boolean is24HourView) {
        if (mIs24HourView == is24HourView) {
            return;
        }
        mIs24HourView = is24HourView;
        // cache the current hour since spinner range changes
        int currentHour = getCurrentHour();
        updateHourControl();
        // set value after spinner range is updated
        setCurrentHour(currentHour);
        updateAmPmControl();
    }

    /**
     * @return true if this is in 24 hour view else false.
     */
    public boolean is24HourView() {
        return mIs24HourView;
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentMinute() {
        return mMinuteSpinner.getValue();
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(Integer currentMinute) {
        if (currentMinute == getCurrentMinute()) {
            return;
        }
        mMinuteSpinner.setValue(currentMinute);
        onTimeChanged();
    }

    @Override
    public int getBaseline() {
        return mHourSpinner.getBaseline();
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        onPopulateAccessibilityEvent(event);
        return true;
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);

        int flags = DateUtils.FORMAT_SHOW_TIME;
        if (mIs24HourView) {
            flags |= DateUtils.FORMAT_24HOUR;
        } else {
            flags |= DateUtils.FORMAT_12HOUR;
        }
        mTempCalendar.set(Calendar.HOUR_OF_DAY, getCurrentHour());
        mTempCalendar.set(Calendar.MINUTE, getCurrentMinute());
        String selectedDateUtterance = DateUtils.formatDateTime(mContext,
                mTempCalendar.getTimeInMillis(), flags);
        event.getText().add(selectedDateUtterance);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(TimePicker.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TimePicker.class.getName());
    }

    private void updateHourControl() {
        if (is24HourView()) {
            mHourSpinner.setMinValue(0);
            mHourSpinner.setMaxValue(23);
            mHourSpinner.setFormatter(new TwoDigitFormatter());
        } else {
            mHourSpinner.setMinValue(1);
            mHourSpinner.setMaxValue(12);
            mHourSpinner.setFormatter(null);
        }
    }

    private void updateAmPmControl() {
        if (is24HourView()) {
            if (mAmPmSpinner != null) {
                mAmPmSpinner.setVisibility(View.GONE);
            } else {
                mAmPmButton.setVisibility(View.GONE);
            }
        } else {
            int index = mIsAm ? Calendar.AM : Calendar.PM;
            if (mAmPmSpinner != null) {
                mAmPmSpinner.setValue(index);
                mAmPmSpinner.setVisibility(View.VISIBLE);
            } else {
                mAmPmButton.setText(mAmPmStrings[index]);
                mAmPmButton.setVisibility(View.VISIBLE);
            }
        }
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
    }

    private void onTimeChanged() {
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        if (mOnTimeChangedListener != null) {
            mOnTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute());
        }
    }

    private void setContentDescriptions() {
        // Minute
    	int incrementId = TimePicker.getInternalResrouce("increment", "id");
    	int incrementMinBtn = TimePicker.getInternalResrouce("time_picker_increment_minute_button", "string");
        trySetContentDescription(mMinuteSpinner, incrementId,
                incrementMinBtn);
        
        int decrementId = TimePicker.getInternalResrouce("decrement", "id");
        int decrementMinBtn = TimePicker.getInternalResrouce("time_picker_decrement_minute_button", "string");
        trySetContentDescription(mMinuteSpinner, decrementId,
                decrementMinBtn);
        
        // Hour
        trySetContentDescription(mHourSpinner, incrementId,
                incrementMinBtn);
        trySetContentDescription(mHourSpinner, decrementId,
                decrementMinBtn);
        
        // AM/PM
        if (mAmPmSpinner != null) {
            trySetContentDescription(mAmPmSpinner, incrementId,
                    incrementMinBtn);
            trySetContentDescription(mAmPmSpinner, decrementId,
                    decrementMinBtn);
        }
    }

    private void trySetContentDescription(View root, int viewId, int contDescResId) {
        View target = root.findViewById(viewId);
        if (target != null) {
            target.setContentDescription(mContext.getString(contDescResId));
        }
    }

    private void updateInputState() {
        // Make sure that if the user changes the value and the IME is active
        // for one of the inputs if this widget, the IME is closed. If the user
        // changed the value via the IME and there is a next input the IME will
        // be shown, otherwise the user chose another means of changing the
        // value and having the IME up makes no sense.
        InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (inputMethodManager.isActive(mHourSpinnerInput)) {
                mHourSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(mMinuteSpinnerInput)) {
                mMinuteSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(mAmPmSpinnerInput)) {
                mAmPmSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }
    
    /**
     * 
     * @param name
     * @param defType
     * @return
     */
    private static int getInternalResrouce(String name, String defType) {
    	return getInternalResrouce(name, defType, "com.android.internal");
    }
    
    /**
     * 
     * @param name
     * @param defType
     * @param packageName
     * @return
     */
    private static int getInternalResrouce(String name, String defType, String packageName) {
    	int resId = Resources.getSystem().getIdentifier(name, defType, packageName);
    	return resId;
    }
    
    /**
     * 
     * @param name
     * @param defType
     * @return
     */
    private static int[] getInternalResrouces(String name, String defType, String packageName) {
    	int resId = getInternalResrouce(name, defType, packageName);
    	Log.d("Resource Identifier", "Id = " + resId);
    	return Resources.getSystem().getIntArray(resId);
    }
    
    /**
     * Use a custom NumberPicker formatting callback to use two-digit minutes
     * strings like "01". Keeping a static formatter etc. is the most efficient
     * way to do this; it avoids creating temporary objects on every call to
     * format().
     */
    private static class TwoDigitFormatter implements NumberPicker.Formatter {
        final StringBuilder mBuilder = new StringBuilder();

        char mZeroDigit;
        java.util.Formatter mFmt;

        final Object[] mArgs = new Object[1];

        TwoDigitFormatter() {
            final Locale locale = Locale.getDefault();
            init(locale);
        }

        private void init(Locale locale) {
            mFmt = createFormatter(locale);
            mZeroDigit = getZeroDigit(locale);
        }

        public String format(int value) {
            final Locale currentLocale = Locale.getDefault();
            if (mZeroDigit != getZeroDigit(currentLocale)) {
                init(currentLocale);
            }
            mArgs[0] = value;
            mBuilder.delete(0, mBuilder.length());
            mFmt.format("%02d", mArgs);
            return mFmt.toString();
        }

        private static char getZeroDigit(Locale locale) {
            return LocaleData.get(locale).zeroDigit;
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(mBuilder, locale);
        }
    }
}
