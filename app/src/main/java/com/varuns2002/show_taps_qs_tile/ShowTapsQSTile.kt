package com.varuns2002.show_taps_qs_tile

import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.service.quicksettings.TileService

class ShowTapsQSTile : TileService() {
    override fun onClick() {
        super.onClick()
        if (!Settings.System.canWrite(this)) {
            val intent = Intent("android.settings.action.MANAGE_WRITE_SETTINGS")
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
            return
        }
        val showTapsEnabled: Boolean = try {
            Settings.System.getInt(contentResolver, "show_touches") == 1
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
            false
        }
        if (!showTapsEnabled) {
            Settings.System.putInt(contentResolver, "show_touches", 1)
            qsTile.icon = Icon.createWithResource(
                this, resources.getIdentifier("ic_show_taps", "drawable", packageName)
            )
            qsTile.label = "Hide Taps"
            qsTile.state = 2
        } else {
            Settings.System.putInt(contentResolver, "show_touches", 0)
            qsTile.icon = Icon.createWithResource(
                this, resources.getIdentifier("ic_hide_taps", "drawable", packageName)
            )
            qsTile.label = "Show Taps"
            qsTile.state = 1
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        val showTapsEnabled: Boolean = try {
            Settings.System.getInt(contentResolver, "show_touches") == 1
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
            false
        }
        if (showTapsEnabled) {
            qsTile.icon = Icon.createWithResource(
                this, resources.getIdentifier("ic_show_taps", "drawable", packageName)
            )
            qsTile.label = "Hide Taps"
            qsTile.state = 2
        } else {
            qsTile.icon = Icon.createWithResource(
                this, resources.getIdentifier("ic_hide_taps", "drawable", packageName)
            )
            qsTile.label = "Show Taps"
            qsTile.state = 1
        }
        qsTile.updateTile()
    }
}
