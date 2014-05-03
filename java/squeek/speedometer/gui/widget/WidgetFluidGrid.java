package squeek.speedometer.gui.widget;

import squeek.speedometer.gui.IGuiHierarchical;

public class WidgetFluidGrid extends WidgetBase
{
	public int margin = 4;
	public int padding = 0;
	public int columns = 0;
	public int columnWidth = 100;

	public WidgetFluidGrid(IGuiHierarchical parent, int x, int y, int w, int h)
	{
		super(parent, x, y, w, h);
	}

	@Override
	protected void onChildrenChanged()
	{
		super.onChildrenChanged();
		determineLayout();
	}

	public void determineLayout()
	{
		int totalHeightAvailablePerColumn = h - margin * 2 - padding * 2;
		if (totalHeightAvailablePerColumn > 0)
		{
			int totalHeightOfAllChildren = 0;
			int widthOfWidestChild = 0;
			for (IWidget child : children)
			{
				totalHeightOfAllChildren += child.getHeight() + margin * 2 + padding * 2;
				if (child.getWidth() > widthOfWidestChild)
					widthOfWidestChild = child.getWidth();
			}

			columnWidth = widthOfWidestChild + margin * 2 + padding * 2;
			int maxColumns = (int) Math.floor(w / columnWidth);
			int neededColumns = (int) Math.floor(totalHeightOfAllChildren / totalHeightAvailablePerColumn + 1);
			if (neededColumns > maxColumns)
				maxColumns = neededColumns;
			//if (neededColumns == 1 && maxColumns > 1)
			//	neededColumns = 2;

			columns = Math.max(0, Math.min(neededColumns, Math.min(children.size(), maxColumns)));
			//columns = (int) Math.floor(totalHeightOfAllChildren / totalHeightAvailablePerColumn + 1);

			int clampedW = Math.min(w, columnWidth * columns);
			int clampedX = x + (w - clampedW) / 2;

			/*
			for (int i = 0; i < columns; i++)
			{
				int backgroundX = clampedX + i * columnWidth + margin;
				int backgroundY = y + margin;
				//new WidgetBox(getParent(), backgroundX, backgroundY, columnWidth - margin * 2, totalHeightAvailablePerColumn);
			}
			*/

			int[] columnHeights = new int[columns];
			int tallestColumnHeight = 0;
			for (int iColumn = 0, iChildIndex = 0; iChildIndex < children.size(); iChildIndex++, iColumn = (iColumn + 1) % columns)
			{
				columnHeights[iColumn] += children.get(iChildIndex).getHeight() + margin * 2;
				if (columnHeights[iColumn] > tallestColumnHeight)
					tallestColumnHeight = columnHeights[iColumn];
			}

			int columnYOffset = Math.max(0, (h - tallestColumnHeight) / 2);

			int[] columnY = new int[columns];
			for (int iColumn = 0, iChildIndex = 0; iChildIndex < children.size(); iChildIndex++, iColumn = (iColumn + 1) % columns)
			{
				//int numChildrenInColumn = (int) Math.floor(children.size() / columns + 1);
				int elementX = clampedX + iColumn * columnWidth;
				int elementY = y + columnYOffset + columnY[iColumn];

				IWidget child = children.get(iChildIndex);
				elementY += margin;
				child.setPos(elementX + columnWidth / 2 - child.getWidth() / 2, elementY);
				elementY += child.getHeight() + margin;
				columnY[iColumn] = elementY - y - columnYOffset;
			}
		}
	}

}
