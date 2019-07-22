import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequerimiento } from 'app/shared/model/requerimiento.model';

@Component({
  selector: 'jhi-requerimiento-detail',
  templateUrl: './requerimiento-detail.component.html',
  styleUrls: [
    '../../res-conreq/res-conreq.component.scss'
  ]
})
export class RequerimientoDetailComponent implements OnInit {
  // Enfoque del mapa
  lat: any;
  lng: any;
  zoom = 10;
  requerimiento: IRequerimiento;
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });
    // Enfoque del mapa
    this.lat = this.requerimiento.coorLat;
    this.lng = this.requerimiento.coorLong;
    this.zoom = 10;
  }

  previousState() {
    window.history.back();
  }
}
