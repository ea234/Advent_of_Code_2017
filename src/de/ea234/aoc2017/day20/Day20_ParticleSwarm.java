package de.ea234.aoc2017.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 20: Particle Swarm ---
 * https://adventofcode.com/2017/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kz6ik/2017_day_20_solutions/
 * 
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance          498497 
 * 
 * Minimum Manhatten Distance 0  Definition  p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * 
 * Minimum Manhatten Distance 0  Cur Values   X  p   -499496  v      -999  a        -1    Y  p         0  v         0  a         0   Z  p         0  v         0  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * 
 * #### Particle   0  MH Distance        12036890 
 * #### Particle   1  MH Distance         5474978 
 * #### Particle   2  MH Distance         3952345 
 * #### Particle  25  MH Distance         2612750 
 * #### Particle  38  MH Distance         1699402 
 * #### Particle  71  MH Distance         1698357 
 * #### Particle 279  MH Distance          647520 
 * #### Particle 308  MH Distance          628116 
 * 
 * Minimum Manhatten Distance 308  Definition  p=<2978,2082,4280>, v=<-135,-88,-178>, a=<1,0,0>
 * 
 * Minimum Manhatten Distance 308  Cur Values   X  p    369344  v       866  a         1    Y  p    -86006  v       -88  a         0   Z  p   -173898  v      -178  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 308
 * 
 * </pre> 
 */
public class Day20_ParticleSwarm
{
  public static void main( String[] args )
  {
    String test_input = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>;p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>";

    calculatePart01( test_input, 0, true );

    calculate01( getListProd(), 0, true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pBursts, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pBursts, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pBursts, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    /*
     * *******************************************************************************************************
     * Creating the list of particles
     * *******************************************************************************************************
     */

    List< Day20Particle > l1 = new ArrayList< Day20Particle >();

    int index_p = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {

        Day20Particle new_particle = new Day20Particle( index_p, input_str.trim() );

        l1.add( new_particle );

        index_p++;
      }
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    int max_tick_for_simulation_1 = 1000;

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_1; count_ticks++ )
    {
      for ( Day20Particle cur_particle : l1 )
      {
        cur_particle.doTick();
      }
    }

    /*
     * *******************************************************************************************************
     * Find the minimum Manhatten-Distance
     * *******************************************************************************************************
     */

    wl( "" );

    int min_index = 9999;
    long min_mh_distance = Long.MAX_VALUE;

    for ( Day20Particle cur_particle : l1 )
    {
      if ( cur_particle.getManhattenDistance() < min_mh_distance )
      {
        wl( String.format( "#### Particle %3d  MH Distance %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance() ) );

        min_mh_distance = cur_particle.getManhattenDistance();

        min_index = cur_particle.getIndex();
      }
      else
      {
        //wl( String.format( "     Particle %3d  MH Distance %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance() ) );
      }
    }

    /*
     * *******************************************************************************************************
     * Reset for part 2
     * *******************************************************************************************************
     */

    for ( Day20Particle cur_particle : l1 )
    {
      cur_particle.doTick();
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    int max_tick_for_simulation_2 = 10;

    wl( "Removed Items " + checkCollision( l1 ) );

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_2; count_ticks++ )
    {
      for ( Day20Particle cur_particle : l1 )
      {
        cur_particle.doTick();
      }

      wl( "Removed Items " + checkCollision( l1 ) );

    }

    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Definition  " + l1.get( min_index ).getInput() );
    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Cur Values  " + l1.get( min_index ).toString() );
    wl( "" );
    wl( "max_tick_for_simulation " + max_tick_for_simulation_1 );
    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + min_index );
    wl( "" );
  }

  private static int checkCollision( List< Day20Particle > pList )
  {
    List< Day20Particle > del_list = new ArrayList< Day20Particle >();

    for ( int index_a = 0; index_a < pList.size(); index_a++ )
    {
      Day20Particle part_a = pList.get( index_a );

      for ( int index_b = index_a + 1; index_b < pList.size(); index_b++ )
      {
        Day20Particle part_b = pList.get( index_b );

        if ( part_a.checkCollision( part_b ) )
        {
          if ( del_list.contains( part_a ) == false )
          {
            del_list.add( part_a );
          }

          if ( del_list.contains( part_b ) == false )
          {
            del_list.add( part_b );
          }
        }
      }
    }

    for ( Day20Particle cur_particle : del_list )
    {
      pList.remove( cur_particle );
    }

    return del_list.size();
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day20_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
