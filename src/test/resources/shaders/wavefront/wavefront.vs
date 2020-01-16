#version 330 core

uniform mat4 uModel;
uniform mat4 uViewProjection;

in vec3 aPosition;
in vec2 aTexCoord;
in vec3 aNormal;
in vec3 aTangent;

out vec2 vTexCoord;
out vec3 vPosition;
out vec3 vNormal;
out vec3 vTangent;

out mat3 vTBN;

void main() {
    vTexCoord = aTexCoord;
    vTangent = (uModel * vec4(aTangent, 0)).xyz;
    vNormal = (uModel * vec4(aNormal, 0)).xyz;

    vec3 binormal = cross(vTangent, vNormal);

    vTBN = mat3(vTangent, binormal, vNormal);
    vPosition = vec3(uModel * vec4(aPosition, 1.0));
    gl_Position = uViewProjection * uModel * vec4(aPosition, 1.0);
}