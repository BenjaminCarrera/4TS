/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoIngresoDetailComponent } from 'app/entities/tipo-ingreso/tipo-ingreso-detail.component';
import { TipoIngreso } from 'app/shared/model/tipo-ingreso.model';

describe('Component Tests', () => {
  describe('TipoIngreso Management Detail Component', () => {
    let comp: TipoIngresoDetailComponent;
    let fixture: ComponentFixture<TipoIngresoDetailComponent>;
    const route = ({ data: of({ tipoIngreso: new TipoIngreso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoIngresoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoIngresoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoIngresoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoIngreso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
