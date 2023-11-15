package com.example.medhelp

class Model {
    var fiopac: String? = null
    var palata: String? = null

    constructor() {}
    constructor(fiopac: String?,palata: String?) {
        this.fiopac = fiopac
        this.palata = palata
    }
}