/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EsquemaContratacionKodeDetailComponent } from 'app/entities/esquema-contratacion-kode/esquema-contratacion-kode-detail.component';
import { EsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';

describe('Component Tests', () => {
  describe('EsquemaContratacionKode Management Detail Component', () => {
    let comp: EsquemaContratacionKodeDetailComponent;
    let fixture: ComponentFixture<EsquemaContratacionKodeDetailComponent>;
    const route = ({ data: of({ esquemaContratacionKode: new EsquemaContratacionKode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsquemaContratacionKodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EsquemaContratacionKodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsquemaContratacionKodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.esquemaContratacionKode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
