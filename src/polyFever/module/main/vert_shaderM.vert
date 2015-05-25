#version 150

in vec2 position;
uniform mat4 Projection;

in vec2 texcoord;
out vec2 Texcoord;

in vec3 color;
out vec3 Color;

void main()
{
	
	Texcoord = texcoord;
    Color = color;
    gl_Position = vec4(position, 0.0, 1.0)*Projection;
}