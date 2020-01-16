package dev.wintergreen.engine.asset

class AssetNotFoundException(path: String) : Exception("Asset at path \"$path\" was not found.")