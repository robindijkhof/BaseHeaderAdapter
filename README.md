# BaseHeaderAdapter

This is a simple generic BaseHeaderAdapter for the RecyclerView. I found other solutions not very clean.

Usage:
	Create a adapter which extends BaseHeaderAdapter.
	In this adapter:

		1. Create a MyHeader object which extends BaseHeaderAdapter.Header. This object represents your header.
		2. Create a MyHeaderViewHolder which extends HeaderViewHolder. Implement it like any other ViewHolder you would create but this time for your header.
		3. Create a MyItemViewHolder which extends ItemViewHolder. Implement it like any other ViewHolder you would create but this time for your item.
		4. Implement all abstract methods.
		
	Use it like any other adapter.