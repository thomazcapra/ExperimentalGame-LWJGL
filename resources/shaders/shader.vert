uniform vec4 posicaoLuz;
varying vec4 cor;

void main(void)
{
   gl_Position = posicaoLuz;
   cor = vec4(1.0, 1.0, 1.0, 1.0);
}