package de.ea234.aoc2017.day07;

import java.util.HashMap;
import java.util.List;

public class Day07Pgm
{
  private String pgm_name       = "";

  private int    pgm_weight     = 0;

  private int    weight_tower   = 0;

  private int    weight_balance = 0;

  List< String > pgm_names      = null;

  public Day07Pgm( String pName, String pWeight )
  {
    pgm_name = pName;

    pgm_weight = Integer.valueOf( pWeight ).intValue();
  }

  public void setPgmsAbove( List< String > pPgmNames )
  {
    pgm_names = pPgmNames;
  }

  public int calcWeight( HashMap< String, Day07Pgm > pHashMapPgms )
  {
    int result_weight = pgm_weight;

    if ( pgm_names != null )
    {
      for ( String pgm_name_above : pgm_names )
      {
        Day07Pgm cur_pgm = pHashMapPgms.get( pgm_name_above );

        result_weight += cur_pgm.calcWeight( pHashMapPgms );
      }
    }

    weight_tower = result_weight;

    return result_weight;
  }

  public int checkBalance( HashMap< String, Day07Pgm > pHashMapPgms )
  {
    if ( pgm_names == null )
    {
      /*
       * No pgms above, pgm is in balance
       */
      return 0;
    }

    int last_weight = -1;

    for ( String pgm_name_above : pgm_names )
    {
      Day07Pgm cur_pgm = pHashMapPgms.get( pgm_name_above );

      if ( last_weight != -1 )
      {
        if ( cur_pgm.getWeightTower() != last_weight )
        {
          weight_balance = Math.abs( cur_pgm.getWeightTower() - last_weight );

          return Math.abs( cur_pgm.getWeightTower() - last_weight );
        }
      }

      last_weight = cur_pgm.getWeightTower();
    }

    /*
     * All pgms above have all the same value
     */
    return 0;
  }

  public String toStringBalance( HashMap< String, Day07Pgm > pHashMapPgms )
  {
    String res_string = "PGM " + pgm_name;

    if ( pgm_names == null )
    {
      res_string += "\n  - has no towers";
    }
    else
    {
      for ( String pgm_name_above : pgm_names )
      {
        Day07Pgm cur_pgm = pHashMapPgms.get( pgm_name_above );

        res_string += "\n  - " + cur_pgm.toString();
      }
    }

    return res_string;
  }

  public int getWeightTower()
  {
    return weight_tower;
  }

  public String toString()
  {
    return String.format( "PGM name %-15s  weight %6d  x-weight %6d  -    %s ", pgm_name, pgm_weight, weight_tower, ( weight_balance == 0 ? "" : weight_balance ) );
  }
}
