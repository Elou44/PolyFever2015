#version 150

in vec2 position;
uniform mat4 Projection;

in vec3 color;
out vec3 Color;

void main()
{
    Color = color;
    gl_Position = vec4(position, 0.0, 1.0)*Projection;
}