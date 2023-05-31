/**************************************************************************
 *  Copyright (C) 2023 by Richard Crook                                   *
 *  https://github.com/dazzle50/JTableFX2                                 *
 *                                                                        *
 *  This program is free software: you can redistribute it and/or modify  *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  This program is distributed in the hope that it will be useful,       *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with this program.  If not, see http://www.gnu.org/licenses/    *
 **************************************************************************/

package rjc.table;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rjc.table.signal.ObservableDouble.ReadOnlyDouble;
import rjc.table.signal.ObservableInteger;
import rjc.table.signal.ObservableInteger.ReadOnlyInteger;

/*************************************************************************************************/
/***************** Base class for table X or Y axis with index to pixel mapping ******************/
/*************************************************************************************************/

public class Axis
{
  // axis index starts at 0 for table body, index of -1 is for axis header
  final static public int             INVALID           = -2;
  final static public int             HEADER            = -1;
  final static public int             FIRSTCELL         = 0;

  // count of body cells on axis
  private ReadOnlyInteger             m_count;

  // variables defining default & minimum cell size (width or height) equals pixels if zoom is 1.0
  private int                         m_defaultSize     = 100;
  private int                         m_minimumSize     = 20;
  private int                         m_headerSize      = 50;
  private ReadOnlyDouble              m_zoom;

  // exceptions to default size, -ve means hidden
  final private Map<Integer, Integer> m_sizeExceptions  = new HashMap<>();

  // cached cell index to start pixel coordinate
  final private ArrayList<Integer>    m_cellStartCache  = new ArrayList<>();

  // observable integer for cached axis size in pixels (includes header)
  private ObservableInteger           m_axisPixelsCache = new ObservableInteger( INVALID );

  /**************************************** constructor ******************************************/
  public Axis( ReadOnlyInteger count, ReadOnlyDouble zoom )
  {
    // check arguments
    if ( count == null || count.get() < 0 )
      throw new IllegalArgumentException( "Bad body cell count = " + count );
    if ( zoom == null )
      throw new NullPointerException( "Bad zoom " + zoom );

    // store private variables
    m_count = count;
    m_zoom = zoom;

    // if axis count changes
    m_count.addListener( x ->
    {
      // set cached axis size to invalid and remove any exceptions beyond count
      m_axisPixelsCache.set( INVALID );
      int old_count = (int) Array.get( x, 1 );
      int new_count = m_count.get();
      if ( new_count < old_count )
        for ( int key : m_sizeExceptions.keySet() )
          if ( key >= new_count )
            m_sizeExceptions.remove( key );

      // truncate cell start cache if new size smaller
      if ( new_count < m_cellStartCache.size() )
        m_cellStartCache.subList( new_count, m_cellStartCache.size() ).clear();
    } );

    // if zoom changes
    m_zoom.addListener( x ->
    {
      // clear cell start cache and axis size cache
      m_cellStartCache.clear();
      m_axisPixelsCache.set( INVALID );
    } );
  }

  /****************************************** getCount *******************************************/
  final public int getCount()
  {
    // return count of body cells on axis
    return m_count.get();
  }

  /*************************************** getDefaultSize ****************************************/
  public int getDefaultSize()
  {
    // return default cell size
    return m_defaultSize;
  }

  /*************************************** getMinimumSize ****************************************/
  public int getMinimumSize()
  {
    // return minimum cell size
    return m_minimumSize;
  }

  /*************************************** getHeaderSize ****************************************/
  public int getHeaderSize()
  {
    // return header cell size
    return m_headerSize;
  }

  /******************************************** zoom *********************************************/
  private int zoom( int size )
  {
    // convenience method to return pixels from size
    return (int) ( size * m_zoom.get() );
  }

  /**************************************** getAxisPixels ****************************************/
  public int getAxisPixels()
  {
    // return axis total size in pixels (including header)
    if ( m_axisPixelsCache.get() == INVALID )
    {
      // cached size is invalid, so re-calculate
      int defaultCount = getCount() - m_sizeExceptions.size();

      int pixels = zoom( m_headerSize );
      for ( int size : m_sizeExceptions.values() )
        if ( size > 0 )
          pixels += zoom( size );

      m_axisPixelsCache.set( pixels + defaultCount * zoom( m_defaultSize ) );
    }

    return m_axisPixelsCache.get();
  }

  public ReadOnlyInteger getAxisPixelsReadOnly()
  {
    // return return read-only version of axis pixels
    return m_axisPixelsCache.getReadOnly();
  }

}
