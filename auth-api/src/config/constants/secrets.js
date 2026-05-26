//variavel de ambiente
const env = process.env;

export const API_SECRET = env.API_SECRET ? env.API_SECRET : "YXV0aC1hcGktc2VjcmV0LWRldi0xMjM0NTY=";
//esse código é um termo encodado em base64 e é necessário para se conectar e gerar um token de acesso