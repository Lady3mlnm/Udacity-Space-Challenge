/*
 * Info about variant of item exchange.
 * Used only for optimization.
 */

import java.util.ArrayList;

public class ExchangeVariant {
    public ArrayList<Item> removedItems = new ArrayList();;
    public ArrayList<Item> addedItems = new ArrayList();;
    public int freeSpace = 0;
}