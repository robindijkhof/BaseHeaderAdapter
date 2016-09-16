package mypackage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ********.R;

/**
 * Created by robin on 12-9-2016.
 */
public abstract class BaseHeaderAdapter<ITEM> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> items;

    private final static int ITEM_VIEW_TYPE_ITEM = 0, ITEM_VIEW_TYPE_HEADER = 1;

    private int itemLayoutId, headerLayoutId;

    public BaseHeaderAdapter(ArrayList<ITEM> items, int itemLayoutId, int headerLayoutId){
        super();
        this.itemLayoutId = itemLayoutId;
        this.headerLayoutId = headerLayoutId;
        this.items = new ArrayList<Object>(items);
        addHeaders();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case ITEM_VIEW_TYPE_ITEM:{
                v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
                viewHolder = getItemViewHolder(v);
                onCreateItemViewHolder((ItemViewHolder) viewHolder);
                break;
            }
            case ITEM_VIEW_TYPE_HEADER:{
                v = LayoutInflater.from(parent.getContext()).inflate(headerLayoutId, parent, false);
                viewHolder = getHeaderViewHolder(v);
                onCreateHeaderViewHolder((HeaderViewHolder) viewHolder);
                break;
            }
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            onBindItemViewHolder((ItemViewHolder)holder, (ITEM)items.get(position));
        }else if(holder instanceof HeaderViewHolder){
            onBindHeaderViewHolder((HeaderViewHolder)holder, (Header)items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof BaseHeaderAdapter.Header){
            return ITEM_VIEW_TYPE_HEADER;
        }else{
            return ITEM_VIEW_TYPE_ITEM;
        }
    }

    public void setData(ArrayList<ITEM> items){
        this.items = new ArrayList<Object>(items);
        addHeaders();
    }

    public Header getFirstHeader(int position){
        if(position == -1) return null;

        int header = getFirstHeaderAbovePosition(position);
        if(header == -1) return null;

        if(items.get(header) instanceof BaseHeaderAdapter.Header){
            return (BaseHeaderAdapter.Header)items.get(header);
        }
        else return null;
    }

    public Object getItem(int position){
        return items.get(position);
    }


    private void addHeaders(){
        if(items.size() > 0) {
            ArrayList<ITEM> itemsUnderSameHeader = new ArrayList<>();
            int headerPosition = 0;

            ITEM previousItem = null;
            for (int i = 0; i < items.size(); i++) {
                ITEM item = (ITEM)items.get(i);
                if(previousItem!= null && shouldAddHeader(previousItem, item)){
                    items.add(headerPosition, createHeader(itemsUnderSameHeader));
                    itemsUnderSameHeader.clear();
                    i++;
                    headerPosition = i;
                }
                itemsUnderSameHeader.add(item);
                previousItem = item;
            }

            items.add(headerPosition, createHeader(itemsUnderSameHeader));
        }
    }

    private int getFirstHeaderAbovePosition(int itemPosition){
        if(itemPosition<items.size() && itemPosition >= 0){
            for(int i = itemPosition; i >= 0; i--){
                if(items.get(i) instanceof BaseHeaderAdapter.Header){
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * ItemViewHolder class. Create a custom MyItemViewHolder object which extends ItemViewHolder.
     */
    public static abstract class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(View view) {
            super(view);
        }
    }

    /**
     * HeaderViewHolder class. Create a custom MyHeaderViewHolder object which extends HeaderViewHolder.
     */
    public static abstract class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    /**
     * Header base object. Your header must extend this base header.
     */
    public abstract class Header{}

    /**
     *  Method to indicate weather a new header should be inserted between previousItem and item
     * @param previousItem the item previous to item
     * @param item the item
     * @return boolean weather a new header should be inserted between the two items.
     */
    abstract boolean shouldAddHeader(ITEM previousItem, ITEM item);

    /**
     * Method to get a header object
     * @param itemsUnderHeader the items which will appear under the header
     * @return a header object
     */
    abstract BaseHeaderAdapter.Header createHeader(ArrayList<ITEM> itemsUnderHeader);

    /**
     * Call when a new HeaderViewHolder is created for the RecyclerView.
     * @param headerViewHolder the newly created HeaderViewHolder
     */
    abstract void onCreateHeaderViewHolder(HeaderViewHolder headerViewHolder);

    /**
     * Call when a new ItemViewHolder is created for the RecyclerView.
     * @param itemViewHolder the newly created ItemViewHolder
     */
    abstract void onCreateItemViewHolder(ItemViewHolder itemViewHolder);

    /**
     * Method the get a new HeaderViewHolder. Your custom HeaderViewHolder should extend HeaderViewHolder
     * @param view the view
     * @return the newly created HeaderViewHolder.
     */
    abstract HeaderViewHolder getHeaderViewHolder(View view);

    /**
     * Method the get a new ItemViewHolder. Your custom ItemViewHolder should extend ItemViewHolder
     * @param view the view
     * @return the newly created ItemViewHolder.
     */
    abstract ItemViewHolder getItemViewHolder(View view);

    /**
     * Called wby the RecyclerView to update the Header. You can set the view values in here.
     * @param headerViewHolder the corresponding HeaderViewHolder
     * @param header the corresponding Header
     */
    abstract void onBindHeaderViewHolder(HeaderViewHolder headerViewHolder, Header header);

    /**
     * Called wby the RecyclerView to update the Item. You can set the view values in here.
     * @param itemViewHolder the corresponding ItemViewHolder
     * @param item the corresponding Item
     */
    abstract void onBindItemViewHolder(ItemViewHolder itemViewHolder, ITEM item);

}
