package com.knu.cloud.screens.instanceCreate

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class Flavor(
    val name: String,
    val vcpus: Int,
    val ram: Int,
    val diskTotal: Int,
    val rootDisk: Int,
    val ephemeralDisk: Int,
    val public: String
)

var possibleFlavorDataSet = mutableListOf(
    Flavor("m1.nano", 1, 128, 1, 1, 0, "예"),
    Flavor("m1.micro", 1, 192, 1, 1, 0, "예"),
    Flavor("cirros256", 1, 256, 1, 1, 0,"예"),
    Flavor("m1.tiny", 1, 512, 1, 1, 0, "예"),
    Flavor("ds512M", 1, 512 , 5, 5, 0 , "예"),
    Flavor("ds1G", 1, 1, 10, 10, 0, "예"),
    Flavor("m1.small", 1, 2, 20, 20, 0, "예"),
    Flavor("ds2G", 2, 2, 10, 10, 0, "예"),
    Flavor("m1.medium", 2, 4, 40, 40, 0, "예"),
    Flavor("ds4G",  4, 4, 20, 20, 0, "예"),
    Flavor("m1.large", 4, 8, 80, 80, 0, "예"),
    Flavor("m1.xlarge", 8, 16, 160, 160, 0, "예")
)
var uploadFlavorDataSet = mutableListOf<Flavor>()

@HiltViewModel
class InstanceCreateViewModel @Inject constructor(

): ViewModel() {
    private val _uploadFlavor = mutableStateOf<List<Flavor>>(emptyList())
    val uploadFlavor: State<List<Flavor>> = _uploadFlavor
    private val _possibleFlavor = mutableStateOf<List<Flavor>>(emptyList())
    val possibleFlavor: State<List<Flavor>> = _possibleFlavor

    init {
        _uploadFlavor.value = uploadFlavorDataSet
        _possibleFlavor.value = possibleFlavorDataSet
    }

    fun uploadFlavor(flavor: Flavor, position: Int) {
        uploadFlavorDataSet.add(flavor)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.removeAt(position)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

    fun deleteFlavor(flavor: Flavor, position: Int) {
        uploadFlavorDataSet.removeAt(position)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.add(flavor)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

}