/**
 * This file is part of ImportPlain library (check README).
 * Copyright (C) 2012-2013 Stanislav Nepochatov
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

package ExportModules;

import Utils.IOControl;

/**
 * Plain export class.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class Plain extends Export.Exporter {
    
    public static String type = "PLAIN";
    
    public static String propertyType = "EXPORT_PLAIN";
    
    /**
     * Constructor redirect.
     * @param givenMessage message to export;
     * @param givenSchema export scheme reference;
     * @param givenSwitch message index updater switch;
     * @param givenDir dir which message came from;
     */
    public Plain(MessageClasses.Message givenMessage, Export.Schema givenSchema, Export.ReleaseSwitch givenSwitch, String givenDir) {
        super(givenMessage, givenSchema, givenSwitch, givenDir);
    }

    @Override
    protected void doExport() {
        String fileName;
        //TODO: make this part according to specs.
        switch (this.currSchema.currConfig.getProperty("plain_naming")) {
            case "HEADER":
                fileName = this.exportedMessage.HEADER;
                break;
            case "INDEX":
                fileName = this.exportedMessage.INDEX;
                break;
            default:
                fileName = this.exportedMessage.INDEX;
        }
        java.io.File exportFile = new java.io.File(this.currSchema.currConfig.getProperty("plain_path") + "/" + fileName);
        try {
            java.io.FileWriter exportWriter = new java.io.FileWriter(exportFile);
            exportWriter.write(this.exportedContent);
            exportWriter.close();
            if ("1".equals(this.currSchema.currConfig.getProperty("opt_log"))) {
                IOControl.serverWrapper.log(IOControl.EXPORT_LOGID + ":" + this.currSchema.name, 3, "прозведено експорт повідомлення " + this.exportedMessage.INDEX);
            }
            exportedMessage.PROPERTIES.add(new MessageClasses.MessageProperty(this.propertyType, "root", this.currSchema.currConfig.getProperty("export_print"), IOControl.serverWrapper.getDate()));
        } catch (java.io.IOException ex) {
            IOControl.serverWrapper.log(IOControl.EXPORT_LOGID + ":" + this.currSchema.name, 1, "неможливо провести запис до файлу " + exportFile.getAbsolutePath());
        }
    }

    @Override
    public void tryRecovery() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void resetState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
