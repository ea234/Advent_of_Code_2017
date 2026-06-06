package de.ea234.aoc2017.day17;

public class Day17Value
{
  private Day17Value previous = null;

  private Day17Value next     = null;

  private int        value;

  public Day17Value( int pValue )
  {
    value = pValue;

    previous = this;

    next = this;
  }

  public void setPrev( Day17Value pPrev )
  {
    previous = pPrev;
  }

  public Day17Value getPrev()
  {
    return previous;
  }

  public void setNext( Day17Value pNext )
  {
    next = pNext;
  }

  public Day17Value getNext()
  {
    return next;
  }

  public int getValue()
  {
    return value;
  }

  public void insertX( Day17Value pInstanceToInsert )
  {
    Day17Value old_next_instance = next;

    next = pInstanceToInsert;

    pInstanceToInsert.setPrev( this );

    old_next_instance.setPrev( pInstanceToInsert );

    pInstanceToInsert.setNext( old_next_instance );
  }
}
