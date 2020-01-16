#version 330 core

out vec4 fColor;

uniform vec3 uCameraPosition;

in vec2 vTexCoord;
in vec3 vNormal;
in vec3 vPosition;
in mat3 vTBN;

void main() {
    fColor = vec4(1f, 1f, 1f, 1f);
}