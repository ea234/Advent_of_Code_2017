package de.ea234.aoc2017.day17;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * --- Day 17: Spinlock ---
 * https://adventofcode.com/2017/day/17
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kc0xw/2017_day_17_solutions/
 *
 * 
 * Elements     2  Forward    3  Cur Position     1 =      5       1
 * Elements     3  Forward    3  Cur Position     1 =      6       0
 * Elements     4  Forward    3  Cur Position     1 =      7       3
 * Elements     5  Forward    3  Cur Position     1 =      8       3
 * Elements     6  Forward    3  Cur Position     1 =      9       3
 * Start list  [0]
 * 
 * Elements     1  Forward    3  Cur Position     0 =      4       0       3      0
 * Exception in thread "main" java.lang.IndexOutOfBoundsException: Index: 3, Size: 1
 *     at java.base/java.util.ArrayList.rangeCheckForAdd(ArrayList.java:838)
 *     at java.base/java.util.ArrayList.add(ArrayList.java:510)
 *     at de.ea234.aoc2017.day17.Day17_Spinlock.calculate01(Day17_Spinlock.java:66)
 *     at de.ea234.aoc2017.day17.Day17_Spinlock.main(Day17_Spinlock.java:31)
 * 
 * </pre>
 */
public class Day17_Spinlock
{
  public static void main( String[] args )
  {
    testGetG( 2, 3, 1 );
    testGetG( 3, 3, 1 );
    testGetG( 4, 3, 1 );
    testGetG( 5, 3, 1 );
    testGetG( 6, 3, 1 );

    calculate01( 3, false );

    System.exit( 0 );
  }

  private static void calculate01( int pForwardCount, boolean pKnzDebug )
  {
    int result_part_01 = 0;

    int result_part_02 = 0;

    int index_current_position = 0;

    List< Integer > int_list = new ArrayList< Integer >();

    int step_count_current = 1;
    int step_count_max = 10;

    int_list.add( Integer.valueOf( 0 ) );

    wl( "Start list  " + int_list.toString() );
    wl( "" );

    while ( step_count_current < step_count_max )
    {
      int index_insert = testInsertTheWrongWay( int_list, pForwardCount, index_current_position );

      if ( index_insert == int_list.size() )
      {
        int_list.add( Integer.valueOf( step_count_current ) );

        index_current_position = int_list.size();
      }
      else
      {
        int_list.add( index_insert, Integer.valueOf( step_count_current ) );

        index_current_position = index_insert;
      }

      wl( getDebugString( int_list, int_list.size(), pForwardCount, index_current_position ) );

      step_count_current++;
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static String getDebugString( List< Integer > int_list, int pAnzahlElemente, int pAnzahlX, int pStart )
  {
    int index_a = pAnzahlElemente + pAnzahlX;

    int index_b = index_a % pAnzahlElemente;

    String res_string = String.format( "Elements %5d  Forward %4d  Cur Position %5d =  %5d   %5d    ", pAnzahlElemente, pAnzahlX, pStart, index_a, index_b );

    int cur_pos_idx = pStart - 1;
    int max_idx = Math.min( int_list.size(), 30 );

    for ( int idx = 0; idx < max_idx; idx++ )
    {
      if ( idx == cur_pos_idx )
      {
        res_string += "(" + int_list.get( idx ) + ") ";
      }
      else
      {
        res_string += int_list.get( idx ) + " ";
      }
    }

    return res_string;
  }

  private static int testGetG( int pAnzahlElemente, int pAnzahlX, int pStart )
  {
    int index_a = pAnzahlElemente + pAnzahlX;

    int index_b = index_a % pAnzahlElemente;

    wl( String.format( "Elements %5d  Forward %4d  Cur Position %5d =  %5d   %5d", pAnzahlElemente, pAnzahlX, pStart, index_a, index_b ) );

    return index_b;
  }

  private static int testInsertTheWrongWay( List< Integer > int_list, int pAnzahlX, int pStart )
  {
    int element_count = int_list.size();

    int index_a = element_count + pAnzahlX;

    int index_b = index_a % element_count;

    int counter = 0;

    int index_ins = 0;

    while ( counter < pAnzahlX )
    {
      if ( index_ins == element_count )
      {
        index_ins = 0;
      }

      index_ins++;
      counter++;
    }

    if ( index_ins == element_count )
    {
      index_ins = 0;
    }

    wl( String.format( "Elements %5d  Forward %4d  Cur Position %5d =  %5d   %5d   %5d  %5d", element_count, pAnzahlX, pStart, index_a, index_b, counter, index_ins ) );

    return counter;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}