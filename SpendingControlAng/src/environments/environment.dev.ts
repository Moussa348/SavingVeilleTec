const host = "192.168.0.184";
const port = "4444";

export const environment = {
  production: false,
  link: `http://${host}:${port}`
};

export const STORAGE_KEY = 'token';
export const ALLOWED_URLS_DEV = [host + ":" + port];
