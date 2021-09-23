import { STORAGE_KEY } from './constant';
import decode from 'jwt-decode';
import { NodeWithI18n } from '@angular/compiler';

export function getId() {
  const token = sessionStorage.getItem(STORAGE_KEY);
  return token != null ? decode<any>(token).personId : null;
}

export function isTokenExpired() {
  const token = sessionStorage.getItem(STORAGE_KEY);
  return decode<any>(token).exp * 1000 < new Date().getTime();
}
