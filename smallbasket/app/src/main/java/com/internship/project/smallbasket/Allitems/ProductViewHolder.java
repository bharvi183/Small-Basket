package com.internship.project.smallbasket.AllItems;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.internship.project.smallbasket.Java_files.ItemClickListner;
import com.internship.project.smallbasket.R;

class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public RelativeLayout layout;
    public TextView txtProductName, txtProductDescription, txtProductPrice ,txtNoofitems ,txtset_noofitems;
    public ImageView imageView , addtocartimage;
    public ItemClickListner listner;
    public Button removecart;


    public ProductViewHolder(View itemView ) {
        super(itemView);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        addtocartimage = (ImageView) itemView.findViewById(R.id.addtocart_imagebutton);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        removecart = (Button) itemView.findViewById(R.id.remove_cart);
        txtNoofitems=(TextView) itemView.findViewById(R.id.d_noofitem);
        txtset_noofitems=(TextView) itemView.findViewById(R.id.set_noofitem);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }


}

