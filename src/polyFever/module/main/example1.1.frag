#version 150

uniform vec3 triangleColor;

out vec4 outputColor;

void main()
{
	outputColor = vec4(triangleColor, 1.0);
}
