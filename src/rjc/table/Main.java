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

import rjc.table.signal.ObservableDouble;
import rjc.table.signal.ObservableInteger;

public class Main
{
  /******************************************** main *********************************************/
  public static void main( String[] args )
  {
    // entry point for demo application startup
    Utils.trace( "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ JTableFX2 demo started ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
    Utils.trace( "JTableFX2 VERSION = '" + Utils.VERSION + "'", args );

    ObservableInteger columnCount = new ObservableInteger( 5 );
    ObservableDouble zoomFactor = new ObservableDouble( 1.0 );

    Axis axis = new Axis( columnCount.getReadOnly(), zoomFactor.getReadOnly() );
    Utils.trace( axis.getAxisPixels() );

    columnCount.set( 7 );
    Utils.trace( axis.getAxisPixels() );

    Utils.trace( "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ JTableFX2 demo ended ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
  }

}
