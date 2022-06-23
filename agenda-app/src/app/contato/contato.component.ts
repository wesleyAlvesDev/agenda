import { DataSource } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Contato } from '../model/contato';
import { ContatoService } from '../service/contato.service';
import { MatDialog } from "@angular/material/dialog";
import { ContatoDetalheComponent } from "../modal/contato-detalhe/contato-detalhe.component";
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.css']
})
export class ContatoComponent implements OnInit {

  public dataSource: MatTableDataSource<Contato>;

  public dataSourceFavorito: MatTableDataSource<Contato>;

  public displayedColumns: string[] = ['id', 'nome', 'email', 'favorito'];

  public contatoForm: FormGroup;

  public totalDeContatos: number = 0;

  public pageSize: number = 10;

  public pageList: number = 0;

  public pageEvent;

  public totalDeContatosFavoritos: number = 0;

  public pageSizeFavorito: number = 10;

  public pageListFavorito: number = 0;

  public fotoPerfil = false;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor(
    private formBuilder: FormBuilder,
    private contatoService: ContatoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.inicializarFomrulario();
    this.listContatos();
    this.listFavorito();
  }

  public pageNavigations(event?: PageEvent) {
    this.pageList = event.pageIndex;
    this.pageSize = event.pageSize;
    this.listContatos();
  }

  public pageNavigationFavorito(event?: PageEvent) {
    this.pageListFavorito = event.pageIndex;
    this.pageSizeFavorito = event.pageSize;
    this.listContatos();
  }

  listFavorito() {
    this.pageListFavorito = this.pageListFavorito == null || this.pageListFavorito == undefined ? 0 : this.pageListFavorito;
    this.pageSizeFavorito = this.pageSizeFavorito == null || this.pageSizeFavorito == undefined ? 10 : this.pageSizeFavorito;
    this.contatoService.listFavorito(this.pageListFavorito, this.pageSizeFavorito).subscribe(response => {
      
      this.totalDeContatosFavoritos = response.totalElements;
      this.pageSizeFavorito = response.pageSize;
      this.dataSourceFavorito = new MatTableDataSource<Contato>(response.content);
      this.dataSourceFavorito.data = response.content;

      this.paginator._intl.itemsPerPageLabel = 'Itens por página';
      this.paginator._intl.nextPageLabel = 'Página seguinte';
      this.paginator._intl.previousPageLabel = 'Página anterior';
      this.paginator._intl.firstPageLabel = 'Primeira página';
      this.paginator._intl.lastPageLabel = 'Ultima página';
    });
  }

  favoritarContato(contato: Contato) {
    contato.favorito = !contato.favorito
    this.contatoService.favoritar(contato).subscribe(response=> {
      this.listFavorito();
      this.listContatos();
    })
  }

  public listContatos() {
    this.pageList = this.pageList == null || this.pageList == undefined ? 0 : this.pageList;
    this.pageSize = this.pageSize == null || this.pageSize == undefined ? 10 : this.pageSize;
    this.contatoService.listAll(this.pageList, this.pageSize).subscribe(response => {
      
      this.totalDeContatos = response.totalElements;
      this.pageSize = response.pageSize;
      this.dataSource = new MatTableDataSource<Contato>(response.content);
      this.dataSource.data = response.content;
      
      this.paginator._intl.itemsPerPageLabel = 'Itens por página';
      this.paginator._intl.nextPageLabel = 'Página seguinte';
      this.paginator._intl.previousPageLabel = 'Página anterior';
      this.paginator._intl.firstPageLabel = 'Primeira página';
      this.paginator._intl.lastPageLabel = 'Ultima página';
    });
  }

  public salvarContato() {
    if (!this.contatoForm.valid) {
      return;
    }

    const contato: Contato = this.contatoForm.getRawValue();
    this.contatoService.saveContato(contato).subscribe(response => {
      this.snackBar.open("Contato salvo com sucesso!", 'X', {
        duration: 2000
      });
      this.liparFormulario();
    });
  }

  public uploadFoto(event, contato: Contato) {
    const files = event.target.files;
    if (files) {
      const foto = files[0];
      const formData: FormData = new FormData();
      formData.append("foto", foto);
      this.contatoService.upload(contato, formData).subscribe(response => {
        this.listContatos();
        this.listFavorito();
      })
    }
  }

  visualizarContato(contato) {
    this.dialog.open(ContatoDetalheComponent, {
      width: '500px',
      height: '550px',
      data: contato
    })
  }

  private liparFormulario() {
    this.contatoForm.reset();
    this.ngOnInit();
  }

  private inicializarFomrulario() {
    this.contatoForm = this.formBuilder.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      favorito: [false]
    });
  }
}
