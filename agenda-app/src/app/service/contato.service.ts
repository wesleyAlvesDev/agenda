import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Contato } from '../model/contato';

@Injectable({
  providedIn: 'root'
})
export class ContatoService {

  public urlBase: string = `${environment.urlBase}/api/contatos`;

  constructor(private http: HttpClient) { }

  public saveContato(contato: Contato): Observable<Contato> {
    return this.http.post<Contato>(this.urlBase, contato);
  }

  public listAll(offset?: number, limit?: number): Observable<any> {
    const url = `${this.urlBase}?page=${offset}&size=${limit}`;
    return this.http.get<any>(url);
  }

  public listFavorito(offset?: number, limit?: number): Observable<any> {
    const url = `${this.urlBase}/favoritos?page=${offset}&size=${limit}`;
    return this.http.get<any>(url);
  }
 
  public favoritar(contato: Contato): Observable<any> {
    const url = `${this.urlBase}/${contato.id}/favorito`;
    return this.http.patch<any>(url, null);
  }

  public upload(contato: Contato, formData: FormData): Observable<any> {
    const url = `${this.urlBase}/${contato.id}/foto`;
    return this.http.put<any>(url, formData);
  }
}
