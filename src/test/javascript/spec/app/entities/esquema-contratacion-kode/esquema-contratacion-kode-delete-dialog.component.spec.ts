/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EsquemaContratacionKodeDeleteDialogComponent } from 'app/entities/esquema-contratacion-kode/esquema-contratacion-kode-delete-dialog.component';
import { EsquemaContratacionKodeService } from 'app/entities/esquema-contratacion-kode/esquema-contratacion-kode.service';

describe('Component Tests', () => {
  describe('EsquemaContratacionKode Management Delete Component', () => {
    let comp: EsquemaContratacionKodeDeleteDialogComponent;
    let fixture: ComponentFixture<EsquemaContratacionKodeDeleteDialogComponent>;
    let service: EsquemaContratacionKodeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsquemaContratacionKodeDeleteDialogComponent]
      })
        .overrideTemplate(EsquemaContratacionKodeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsquemaContratacionKodeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsquemaContratacionKodeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
