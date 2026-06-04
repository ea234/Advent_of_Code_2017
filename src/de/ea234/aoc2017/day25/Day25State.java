package de.ea234.aoc2017.day25;

import java.util.List;

public class Day25State
{
  private String      name;

  private Day25Action action_a = null;

  private Day25Action action_b = null;

  public Day25State( List< String > pListInput, int pIndex )
  {
    name     = "" + pListInput.get( pIndex ).charAt( 9 );

    action_a = new Day25Action( pListInput, pIndex + 1 );

    action_b = new Day25Action( pListInput, pIndex + 5 );
  }

  public String getName()
  {
    return name;
  }

  public Day25Action getAction( int pCurrentValue )
  {
    if ( action_a.getCurrentValue() == pCurrentValue )
    {
      return action_a;
    }

    return action_b;
  }

  public String toString()
  {
    return "State " + name + " " + action_a.toString() + "    " + action_b.toString();
  }
}
