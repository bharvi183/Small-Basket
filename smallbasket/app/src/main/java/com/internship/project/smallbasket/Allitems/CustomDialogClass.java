package com.internship.project.smallbasket.AllItems;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.internship.project.smallbasket.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no ,remove;
    private NumberPicker picker1;
    private String[] pickerVals;
    private RecyclerView recyclerView;
    String value;
    DatabaseReference mreferance , madddata,mnoofitem , ProductsRef ,msetdata;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        remove=(Button) findViewById(R.id.btn_remove);
        no.setOnClickListener(this);
        picker1 = findViewById(R.id.numberpicker);
        picker1.setMaxValue(9);
        picker1.setMinValue(0);
        pickerVals  = new String[] { "1", "2", "3", "4" ,"5","6","7","8","9","10"};
        picker1.setDisplayedValues(pickerVals);
        Products object = new Products();


//        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                int valuePicker1 = picker1.getValue();
//                //Log.d("picker value", pickerVals[valuePicker1]);
//            }
//        });

    }

    public String getNumPicker(){
        return pickerVals[picker1.getValue()];
    }

    public Button getYesButton(){
        return yes;
    }

    public Button getRemove() {
        yes.setVisibility(View.GONE);
        remove.setVisibility(View.VISIBLE);
        return remove;
    }

    public Button getNo() {
        return no;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.btn_yes:
//                setdata2();
//                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        ///dismiss();
    }

//    private void setdata2() {
//
//        int picker =picker1.getValue();
//        value = String.valueOf(picker);
//        Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
//
//
//    }

}
